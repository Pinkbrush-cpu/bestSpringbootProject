package com.czy.template.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class examClazz {
    private Long exam_clazz_id;
    private Long exam_id;
    private Long clazz_id;
    private LocalDateTime exam_start_time;
    private Long exam_time;             //考试时间，分钟
    private String exam_name;
}
