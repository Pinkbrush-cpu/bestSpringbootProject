package com.czy.template.view.dto;

import lombok.Data;

import java.util.List;

@Data
public class PublicExamDTO {
    private Long examId;                   //考试id
    private String title;                 //考试名称
    private List<Integer> questionIds;    //考试题目
    private Double totalScore;               //总分数
    private Integer totalTitle;               //总题目数
    private String exam_uuid;             //考试编号
}
