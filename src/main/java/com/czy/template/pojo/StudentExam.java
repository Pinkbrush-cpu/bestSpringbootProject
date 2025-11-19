package com.czy.template.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentExam {
    private Long recordId;
    private Long studentId;
    private Long examId;
    private Long clazzId;
    private Integer totalScore;       // 总得分（可为空，表示未批改）
    private Integer objectiveScore;   // 客观题得分
    private Integer subjectiveScore;  // 主观题得分
    private String status;            // 状态（NOT_STARTED-未开始，IN_PROGRESS-进行中，SUBMITTED-已提交，GRADED-已批改）
    private LocalDateTime startTime;  // 开始时间
    private LocalDateTime submitTime; // 提交时间
    private Integer duration;         // 实际考试用时（分钟）
    private String ipAddress;         // 考试IP地址（防作弊）
}
