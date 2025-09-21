package com.czy.template.dto;

import lombok.Data;

import java.util.List;

@Data
public class PublicExamDTO {
    private int examId;                   //考试id
    private String title;                 //考试名称
    private List<Integer> questionIds;    //考试题目
    private int totalScore;               //总分数
    private int totalTitles;              //总题目数
}
