package com.czy.template.controller;

import com.czy.template.service.TokenBasedOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pink/admin/online")
public class OnlineUserController {

    @Autowired
    private TokenBasedOnlineService onlineService;

    @GetMapping("/count")
    //获取在线用户数量
    public ResponseEntity<Map<String, Object>> getOnlineCount() {
        Map<String, Object> result = new HashMap<>();
        result.put("onlineCount", onlineService.getOnlineCount());
        result.put("distinctUserCount", onlineService.getDistinctUserCount());
        result.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users")
    //获取在线用户列表
    public ResponseEntity<List<Map<String, Object>>> getOnlineUsers() {
        List<Map<String, Object>> userList = onlineService.getOnlineUsers().stream()
                .map(activity -> {
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("userId", activity.getUserId());
                    userMap.put("username", activity.getUsername());
                    userMap.put("ipAddress", activity.getIpAddress());
                    userMap.put("lastActivity", activity.getLastActivityTime());
                    userMap.put("loginTime", activity.getLoginTime());
                    userMap.put("onlineDuration",
                            System.currentTimeMillis() - activity.getLoginTime());
                    return userMap;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(userList);
    }

    @GetMapping("/statistics")
    //获取在线统计信息
    public ResponseEntity<Map<String, Object>> getStatistics() {
        return ResponseEntity.ok(onlineService.getStatistics());
    }

    @PostMapping("/logout/{userId}")
    //强制用户下线
    public ResponseEntity<Map<String, Object>> forceLogout(
            @PathVariable String userId,
            @RequestHeader("Authorization") String adminToken) {
        // 这里应该添加管理员权限验证

        boolean success = onlineService.forceLogout(userId);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "用户已强制下线" : "用户不在线");

        return ResponseEntity.ok(result);
    }

    @GetMapping("/check/{token}")
    //检查token是否在线
    public ResponseEntity<Map<String, Object>> checkOnlineStatus(@PathVariable String token) {
        Map<String, Object> result = new HashMap<>();
        result.put("online", onlineService.isUserOnline(token));
        result.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(result);
    }
}
