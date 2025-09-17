package com.czy.template.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuestionCreateDTO {
    private Integer score;          // 分值
    private String type;            // 题型
    private String title;           // 题干
    private List<String> options;   // 选项（简答题可为 null）
    private String answer;          // 正确答案
    private String answerText;      // 解析
}