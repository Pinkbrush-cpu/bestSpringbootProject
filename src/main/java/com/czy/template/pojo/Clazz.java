package com.czy.template.pojo;

import lombok.Data;

@Data
public class Clazz {
    private Long class_id;
    private String class_name;
    private Long teacher_id;
    private int student_count;
    private String class_state;
}
