package com.czy.template.view.vo;

import com.czy.template.pojo.Question;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TakeExamVO {
    private Long examId;
    private String examName;
    private Double totalScore;
    private Long examTime;
    private LocalDateTime examStartTime;
    private List<Question> questions;
}
