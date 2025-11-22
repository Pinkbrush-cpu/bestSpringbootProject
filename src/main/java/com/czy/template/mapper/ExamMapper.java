package com.czy.template.mapper;

import com.czy.template.pojo.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ExamMapper {

    @Select("select * from exam where create_id = #{id}")
    List<Exam> getExam(Long id);
}
