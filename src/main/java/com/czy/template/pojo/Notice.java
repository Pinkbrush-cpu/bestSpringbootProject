package com.czy.template.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Notice {
    private Long noticeId;                     //公告编号
    private Long createMid;               //创建管理员ID
    private String title;                 //公告题目
    private String content;               //公告选项
    private String target;                //目标用户
    private LocalDateTime createTime;     //发布时间
}
