package com.czy.template.view.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExamClazzVO {
    private Long examId;
    private LocalDateTime examStartTime;
    private Long examTime;                  //考试时间，分钟
    private LocalDateTime createTime;
    private String state;
    private String examName;
    private int totalScore;
    private Integer totalTitle;
}
