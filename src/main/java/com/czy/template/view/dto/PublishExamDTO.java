package com.czy.template.view.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PublishExamDTO {
    private Long examId;
    private Long clazzId;
    private LocalDateTime examStartTime;
    private Long examTime;
}
