package com.czy.template.mapper;

import com.czy.template.pojo.Exam;
import com.czy.template.pojo.Question;
import com.czy.template.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherMapper {

    //插入创建的题目
    @Insert("INSERT INTO question(create_id,type,title,options,answer,score) VALUES (#{create_id},#{type},#{title},#{options},#{answer},#{score})")
    int createQuestion(Question question);

    //根据题目id搜索题目
    @Select("SELECT * FROM question where q_id = #{q_id}")
    Question selectQuestion(int q_id);

    //根据教师id搜索所有创建的题目
    @Select("SELECT * FROM question where create_id = #{create_id}")
    List<Question> selectAllQuestion(int create_id);

    //根据教师id搜索考试卷
    @Select("SELECT * FROM exam where create_id = #{create_id}")
    List<Exam> selectAllExam(int create_id);

}
