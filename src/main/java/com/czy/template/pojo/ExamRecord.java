package com.czy.template.pojo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ExamRecord {
    private Integer recordId;          //考试记录id
    private Integer examId;            //关联的考试id
    private Integer examClazzId;       //考试发布到班级id
    private Integer studentId;         //学生id
    private LocalDateTime submitTime;  //提交考试时间
    private BigDecimal autoScore;      //客观题分数
    private BigDecimal manualScore;    //主观题分数
    private BigDecimal score;          //总分
    private String answers;            //回答列表，使用json存储
}
