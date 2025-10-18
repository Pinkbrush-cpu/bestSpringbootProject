package com.czy.template.util;

import com.czy.template.service.TokenBasedOnlineService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class OnlineUserInterceptorUtil implements HandlerInterceptor {

    @Autowired
    private TokenBasedOnlineService onlineService;

    @Autowired
    private JwtUtil jwtTokenUtil; // 假设你有JWT工具类

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 排除一些不需要统计的路径
        if (shouldExclude(request.getRequestURI())) {
            return true;
        }

        // 从请求中获取token
        String token = getTokenFromRequest(request);
        if (token != null && jwtTokenUtil.validateToken(token)) {
            // 更新用户活动时间
            onlineService.updateUserActivity(token);
        }

        return true;
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return request.getParameter("token");
    }

    private boolean shouldExclude(String uri) {
        return uri.startsWith("/api/online") ||
                uri.contains("/swagger") ||
                uri.contains("/v2/api-docs") ||
                uri.endsWith(".css") ||
                uri.endsWith(".js") ||
                uri.endsWith(".ico");
    }
}
