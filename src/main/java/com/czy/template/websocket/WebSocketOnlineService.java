package com.czy.template.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class WebSocketOnlineService {

    private final Map<String, UserSession> userSessions = new ConcurrentHashMap<>(); // userId -> UserSession
    private final Map<String, String> sessionToUser = new ConcurrentHashMap<>(); // sessionId -> userId

    public static class UserSession {
        private String userId;
        private String username;
        private String userType;
        private String token;
        private String ipAddress;
        private long loginTime;
        private long lastActivityTime;
        private WebSocketSession webSocketSession;

        public UserSession(String userId, String username, String userType, String token,
                           String ipAddress, WebSocketSession session) {
            this.userId = userId;
            this.username = username;
            this.userType = userType;
            this.token = token;
            this.ipAddress = ipAddress;
            this.webSocketSession = session;
            this.loginTime = System.currentTimeMillis();
            this.lastActivityTime = System.currentTimeMillis();
        }

        // getter methods
        public String getUserId() { return userId; }
        public String getUsername() { return username; }
        public String getUserType() { return userType; }
        public long getLoginTime() { return loginTime; }
        public long getLastActivityTime() { return lastActivityTime; }
        public WebSocketSession getWebSocketSession() { return webSocketSession; }

        public void updateActivity() {
            this.lastActivityTime = System.currentTimeMillis();
        }

        public boolean isActive() {
            return System.currentTimeMillis() - lastActivityTime < 300000; // 5分钟无活动视为离线
        }
    }

    // 用户登录（建立WebSocket连接）
    public void userLogin(String sessionId, String userId, String username, String userType,
                          String token, String ipAddress, WebSocketSession webSocketSession) {
        UserSession session = new UserSession(userId, username, userType, token, ipAddress, webSocketSession);
        userSessions.put(userId, session);
        sessionToUser.put(sessionId, userId);

        System.out.println("✅ 用户登录在线: " + username + " (" + userId + ")");
        System.out.println("📊 当前在线用户数: " + userSessions.size());

        // 广播在线人数更新
        broadcastOnlineStats();
    }

    // 用户登出或断开连接
    public void userLogout(String sessionId) {
        String userId = sessionToUser.remove(sessionId);
        if (userId != null) {
            UserSession session = userSessions.remove(userId);
            if (session != null) {
                System.out.println("✅ 用户退出在线: " + session.getUsername() + " (" + userId + ")");
                System.out.println("📊 剩余在线用户数: " + userSessions.size());
            }
        }

        // 广播在线人数更新
        broadcastOnlineStats();
    }

    // 强制用户下线（管理员功能）
    public boolean forceLogout(String userId) {
        UserSession session = userSessions.remove(userId);
        if (session != null) {
            try {
                session.getWebSocketSession().close(CloseStatus.NORMAL);
                sessionToUser.values().removeIf(id -> id.equals(userId));
                broadcastOnlineStats();
                return true;
            } catch (IOException e) {
                System.err.println("强制用户下线错误: " + e.getMessage());
            }
        }
        return false;
    }

    // 获取在线用户统计
    public Map<String, Object> getOnlineStats() {
        cleanupInactiveUsers();

        Map<String, Object> stats = new HashMap<>();
        stats.put("onlineCount", userSessions.size());

        // 按用户类型统计
        long adminCount = userSessions.values().stream()
                .filter(session -> "admin".equals(session.getUserType()))
                .count();
        long userCount = userSessions.values().stream()
                .filter(session -> "user".equals(session.getUserType()))
                .count();

        stats.put("adminCount", adminCount);
        stats.put("userCount", userCount);

        // 在线用户列表
        List<Map<String, Object>> onlineUsers = userSessions.values().stream()
                .map(session -> {
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("userId", session.getUserId());
                    userInfo.put("username", session.getUsername());
                    userInfo.put("userType", session.getUserType());
                    userInfo.put("loginTime", session.getLoginTime());
                    userInfo.put("lastActivity", session.getLastActivityTime());
                    userInfo.put("onlineDuration", System.currentTimeMillis() - session.getLoginTime());
                    return userInfo;
                })
                .collect(Collectors.toList());

        stats.put("onlineUsers", onlineUsers);

        return stats;
    }

    // 清理不活跃用户
    private void cleanupInactiveUsers() {
        long currentTime = System.currentTimeMillis();
        userSessions.entrySet().removeIf(entry -> {
            if (!entry.getValue().isActive()) {
                String userId = entry.getKey();
                System.out.println("🕒 清理不活跃用户: " + entry.getValue().getUsername());
                sessionToUser.values().removeIf(id -> id.equals(userId));
                return true;
            }
            return false;
        });
    }

    // 广播在线统计信息
    private void broadcastOnlineStats() {
        Map<String, Object> stats = getOnlineStats();
        stats.put("type", "online_stats");
        stats.put("timestamp", System.currentTimeMillis());

        // 向所有在线用户广播
        userSessions.values().forEach(session -> {
            if (session.getWebSocketSession().isOpen()) {
                try {
                    String message = new ObjectMapper().writeValueAsString(stats);
                    session.getWebSocketSession().sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    System.err.println("广播消息错误: " + e.getMessage());
                }
            }
        });
    }
}