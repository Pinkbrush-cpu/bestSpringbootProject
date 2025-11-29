package com.czy.template.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerDetail {
    private Long detailId;
    private Integer recordId;
    private Integer questionId;
    private String studentAnswer;
    private String correctAnswer;
    private Double scoreFormat;
    private String teacherComment;
    private LocalDateTime createTime;
}
