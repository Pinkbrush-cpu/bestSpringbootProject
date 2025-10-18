package com.czy.template.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class OnlineUserWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketOnlineService onlineService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OnlineUserWebSocketHandler(WebSocketOnlineService onlineService) {
        this.onlineService = onlineService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        try {
            // 从查询参数获取用户信息
            Map<String, String> params = extractParamsFromSession(session);
            String token = params.get("token");
            String userId = params.get("userId");
            String username = params.get("username");
            String userType = params.get("userType") != null ? params.get("userType") : "user";

            if (userId == null || username == null) {
                session.close(CloseStatus.NOT_ACCEPTABLE);
                return;
            }

            // 记录用户登录
            String clientIp = getClientIp(session);
            onlineService.userLogin(session.getId(), userId, username, userType, token, clientIp, session);

            // 发送欢迎消息
            sendWelcomeMessage(session, userId, username);

        } catch (Exception e) {
            session.close(CloseStatus.SERVER_ERROR);
        }
    }

    private String getClientIp(WebSocketSession session) {
        String clientIp = "unknown";

        try {
            // 方法1: 尝试从握手属性获取
            clientIp = getIpFromHandshakeAttributes(session);
            if (!"unknown".equals(clientIp)) {
                return clientIp;
            }

            // 方法2: 从远程地址获取
            clientIp = getIpFromRemoteAddress(session);
            if (!"unknown".equals(clientIp)) {
                return clientIp;
            }

        } catch (Exception e) {
            System.err.println("获取客户端IP异常: " + e.getMessage());
        }

        return clientIp;
    }

    private String getIpFromHandshakeAttributes(WebSocketSession session) {
        try {
            Map<String, Object> attributes = session.getAttributes();

            // 尝试不同的属性名
            String[] possibleAttributeNames = {
                    "HTTP_REQUEST",
                    "javax.servlet.http.HttpServletRequest",
                    "httpServletRequest",
                    "request"
            };

            for (String attrName : possibleAttributeNames) {
                Object requestObj = attributes.get(attrName);
                if (requestObj instanceof HttpServletRequest) {
                    HttpServletRequest request = (HttpServletRequest) requestObj;
                    return extractClientIpFromRequest(request);
                }
            }
        } catch (Exception e) {
            System.err.println("从握手属性获取IP失败: " + e.getMessage());
        }
        return "unknown";
    }

    private String getIpFromRemoteAddress(WebSocketSession session) {
        try {
            if (session.getRemoteAddress() != null &&
                    session.getRemoteAddress().getAddress() != null) {
                String ip = session.getRemoteAddress().getAddress().getHostAddress();
                // 处理本地地址
                if ("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip)) {
                    return "127.0.0.1";
                }
                return ip;
            }
        } catch (Exception e) {
            System.err.println("从远程地址获取IP失败: " + e.getMessage());
        }
        return "unknown";
    }

    private String extractClientIpFromRequest(HttpServletRequest request) {
        // 优先检查代理头
        String[] proxyHeaders = {
                "X-Forwarded-For",
                "X-Real-IP",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR"
        };

        for (String header : proxyHeaders) {
            String ip = request.getHeader(header);
            if (isValidIp(ip)) {
                System.out.println("从头部 " + header + " 获取到IP: " + ip);
                return ip.split(",")[0].trim(); // 处理多个IP的情况
            }
        }

        // 使用远程地址
        String remoteAddr = request.getRemoteAddr();
        System.out.println("使用远程地址: " + remoteAddr);
        return remoteAddr;
    }

    private boolean isValidIp(String ip) {
        return ip != null &&
                !ip.isEmpty() &&
                !"unknown".equalsIgnoreCase(ip) &&
                !"0:0:0:0:0:0:0:1".equals(ip);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        onlineService.userLogout(session.getId());
    }

    private Map<String, String> extractParamsFromSession(WebSocketSession session) {
        Map<String, String> params = new HashMap<>();
        String query = session.getUri().getQuery();
        if (query != null) {
            for (String param : query.split("&")) {
                System.out.println("param是" + param);
                String[] keyValue = param.split("=", 2);
                if (keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return params;
    }

    private void sendWelcomeMessage(WebSocketSession session, String userId, String username) throws IOException, IOException {
        Map<String, Object> welcomeMsg = Map.of(
                "type", "welcome",
                "message", "欢迎 " + username + "，您已成功上线",
                "userId", userId,
                "timestamp", System.currentTimeMillis()
        );

        String json = objectMapper.writeValueAsString(welcomeMsg);
        session.sendMessage(new TextMessage(json));
    }
}