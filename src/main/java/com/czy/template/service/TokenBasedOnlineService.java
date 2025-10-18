package com.czy.template.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class TokenBasedOnlineService {
    private final ConcurrentHashMap<String, UserActivity> activeUsers = new ConcurrentHashMap<>();
    private static final long ACTIVITY_TIMEOUT = 30 * 60 * 1000; // 30分钟无活动视为离线

    @Data
    @AllArgsConstructor
    public static class UserActivity {
        private long userId;
        private String username;
        private String token;
        private long lastActivityTime;
        private String ipAddress;
        private String userAgent;
        private long loginTime;
    }

    // 用户登录时调用
    public void userLogin(long userId, String username, String token, String ip, String userAgent) {
        UserActivity activity = new UserActivity(
                userId, username, token,
                System.currentTimeMillis(), ip, userAgent,
                System.currentTimeMillis()
        );
        activeUsers.put(token, activity);
        System.out.println("用户登录: " + username + ", 当前在线人数: " + activeUsers.size());
    }

    // 用户活动时更新（每次请求调用）
    public void updateUserActivity(String token) {
        UserActivity activity = activeUsers.get(token);
        if (activity != null) {
            activity.setLastActivityTime(System.currentTimeMillis());
            activeUsers.put(token, activity);
        }
    }

    // 用户登出时调用
    public void userLogout(String token) {
        UserActivity removed = activeUsers.remove(token);
        if (removed != null) {
            System.out.println("用户登出: " + removed.getUsername() + ", 剩余在线人数: " + activeUsers.size());
        }
    }

    // 强制踢出用户
    public boolean forceLogout(String userId) {
        List<String> tokensToRemove = activeUsers.entrySet().stream()
                .filter(entry -> userId.equals(entry.getValue().getUserId()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        tokensToRemove.forEach(activeUsers::remove);
        return !tokensToRemove.isEmpty();
    }

    // 获取在线人数
    public int getOnlineCount() {
        cleanupInactiveUsers();
        return activeUsers.size();
    }

    // 获取独立用户数（同一用户多设备登录算一个）
    public int getDistinctUserCount() {
        cleanupInactiveUsers();
        return (int) activeUsers.values().stream()
                .map(UserActivity::getUserId)
                .distinct()
                .count();
    }

    // 获取在线用户列表
    public List<UserActivity> getOnlineUsers() {
        cleanupInactiveUsers();
        return new ArrayList<>(activeUsers.values());
    }

    // 获取用户的所有活跃会话
    public List<UserActivity> getUserSessions(String userId) {
        return activeUsers.values().stream()
                .filter(activity -> userId.equals(activity.getUserId()))
                .collect(Collectors.toList());
    }

    // 检查token是否有效（用户是否在线）
    public boolean isUserOnline(String token) {
        UserActivity activity = activeUsers.get(token);
        if (activity != null) {
            if (System.currentTimeMillis() - activity.getLastActivityTime() > ACTIVITY_TIMEOUT) {
                activeUsers.remove(token);
                return false;
            }
            return true;
        }
        return false;
    }

    // 清理过期用户（每5分钟执行一次）
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void scheduledCleanup() {
        int before = activeUsers.size();
        cleanupInactiveUsers();
        int after = activeUsers.size();
        if (before != after) {
            System.out.println("定时清理: " + (before - after) + "个过期用户，当前在线: " + after);
        }
    }

    private void cleanupInactiveUsers() {
        long currentTime = System.currentTimeMillis();
        activeUsers.entrySet().removeIf(entry ->
                currentTime - entry.getValue().getLastActivityTime() > ACTIVITY_TIMEOUT
        );
    }

    // 获取统计信息
    public Map<String, Object> getStatistics() {
        cleanupInactiveUsers();
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalSessions", activeUsers.size());
        stats.put("distinctUsers", getDistinctUserCount());
        stats.put("lastCleanup", new Date());

        // 按用户分组统计
            Map<Long, Long> userSessionCount = activeUsers.values().stream()
                    .collect(Collectors.groupingBy(UserActivity::getUserId, Collectors.counting()));
            stats.put("userSessionDistribution", userSessionCount);

        return stats;
    }
}