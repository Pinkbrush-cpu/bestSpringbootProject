package com.czy.template.service;

import com.czy.template.mapper.NoticeMapper;
import com.czy.template.mapper.UserMapper;
import com.czy.template.pojo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeService {

    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    UserMapper userMapper;

    //发布公告
    @Transactional
    public void publish(Notice notice) {
        notice.setCreateTime(LocalDateTime.now());
        noticeMapper.insert(notice);

        List<Long> targetUserIds;
        if ("all".equalsIgnoreCase(notice.getTarget())) {
            targetUserIds = userMapper.findUserIdsByRole(0);
        } else if ("teacher".equalsIgnoreCase(notice.getTarget())) {
            targetUserIds = userMapper.findUserIdsByRole(2);
        } else if ("student".equalsIgnoreCase(notice.getTarget())) {
            targetUserIds = userMapper.findUserIdsByRole(1);
        } else {
            throw new IllegalArgumentException("未知的公告目标对象: " + notice.getTarget());
        }

        for (Long userId : targetUserIds) {
            noticeMapper.insertStatus(userId, notice.getNoticeId());
        }
    }

    //获取未读公告
    public List<Notice> getUnreadNotices(Long userId) {
        return noticeMapper.findUnreadByUserId(userId);
    }

    //用户确认已读
//    public void markAsRead(Long userId, Long noticeId) {
//        noticeMapper.updateReadStatus(userId, noticeId, LocalDateTime.now());
//    }
}
