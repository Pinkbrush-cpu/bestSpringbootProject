package com.czy.template.pojo;

import lombok.Data;

@Data
public class Question {
    private int questionId;              //题目编号
    private Long createId;         //创建教师编号
    private String type;           //题目类型
    private String title;          //题目问题
    private String options;        //题目选项
    private String answerText;     //回答
    private String answer;         //答案
    private int score;             //分数
}
