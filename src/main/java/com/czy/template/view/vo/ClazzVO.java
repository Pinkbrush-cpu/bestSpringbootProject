package com.czy.template.view.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.Year;

@Data
public class ClazzVO implements Serializable {

    private Long classId;
    private String className;
    private String classCode;
    private Long teacherId;
    private String teacherName;
    private Year admissionYear;        //入学年份
    private int studentCount;          //班级人数
    private int studentMaxCount;       //班级最大人数
    private String classState;

}
