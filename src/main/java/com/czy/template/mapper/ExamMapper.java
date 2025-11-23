package com.czy.template.mapper;

import com.czy.template.pojo.Exam;
import com.czy.template.pojo.ExamClazz;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ExamMapper {

    @Select("SELECT * FROM exam where create_id = #{id}")
    List<Exam> getExam(Long id);

    @Insert("INSERT INTO exam_clazz (exam_id,clazz_id,create_id,exam_start_time,exam_time,create_time,state) VALUES (#{examId},#{clazzId},#{createId},#{examStartTime},#{examTime},#{createTime},#{state})")
    boolean publishExamByUuid(ExamClazz examClazz);
}
