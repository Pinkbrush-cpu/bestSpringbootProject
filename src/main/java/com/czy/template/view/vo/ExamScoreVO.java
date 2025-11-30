package com.czy.template.view.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExamScoreVO {
    private Long examClazzId;
    private LocalDateTime examStartTime;
    private LocalDateTime examEndTime;
    private Long examTime;
    private String examName;
    private Double totalScore;
    private Double score;
}
