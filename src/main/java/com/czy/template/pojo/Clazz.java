package com.czy.template.pojo;

import lombok.Data;

import java.time.Year;

@Data
public class Clazz {
    private Long classId;
    private String className;
    private String classCode;         //班级编号
    private Long teacherId;
    private Year admissionYear;       //入学年份
    private Integer studentMaxCount;      //最大学生数
    private String classState;        //状态（启用，禁用，已毕业）
}
