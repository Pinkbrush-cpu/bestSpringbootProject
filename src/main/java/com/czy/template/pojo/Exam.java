package com.czy.template.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Exam {
    private int exam_id;                  //考试id
    private Long create_id;               //创建教师id
    private String title;                 //考试名称
    private String status;                //考试状态
    private String questionIds;           //考试题目
    private int totalScore;               //总分数
    private int totalTitles;              //总题目数
    private String examUuid;             //考试编号，教师可以通过编号来发布考试
}
