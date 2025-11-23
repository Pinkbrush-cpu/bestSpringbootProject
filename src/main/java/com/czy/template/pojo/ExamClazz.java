package com.czy.template.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExamClazz {
    private Long examClazzId;
    private Long examId;
    private Long clazzId;
    private Long createId;
    private LocalDateTime examStartTime;
    private Long examTime;                 //考试时间，分钟
    private LocalDateTime createTime;
    private String state;
}
