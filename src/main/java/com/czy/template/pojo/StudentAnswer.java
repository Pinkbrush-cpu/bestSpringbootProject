package com.czy.template.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentAnswer {
    private Long answerId;
    private Long recordId;           // 关联StudentExam
    private Long questionId;
    private String studentAnswer;    // 学生答案
    private Integer score;           // 该题得分
    private String teacherComment;   // 教师评语
    private LocalDateTime answerTime;
}
