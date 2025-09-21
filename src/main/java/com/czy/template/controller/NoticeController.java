package com.czy.template.controller;

import com.czy.template.pojo.Notice;
import com.czy.template.service.NoticeService;
import com.czy.template.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pink/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    // 管理员发布公告
    @PostMapping("/publishNotice")
    public Result<String> publish(@RequestBody Notice notice) {
        noticeService.publish(notice);
        return Result.ok("发布成功");
    }

    // 用户登录后获取未读公告
    @GetMapping("/unread/{userId}")
    public Result<List<Notice>> getUnreadNotices(@PathVariable Long userId) {
        System.out.println("用户id" + userId);
        return Result.ok(noticeService.getUnreadNotices(userId));
    }

    // 用户确认已读
//    @PostMapping("/read")
//    public ResponseEntity<?> markAsRead(@RequestParam Long userId, @RequestParam Long noticeId) {
//        noticeService.markAsRead(userId, noticeId);
//        return ResponseEntity.ok("已读确认成功");
//    }
}
