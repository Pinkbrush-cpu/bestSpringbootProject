package com.czy.template.mapper;

import com.czy.template.pojo.ExamClazz;
import com.czy.template.view.vo.ExamClazzVO;
import com.czy.template.view.vo.ExamScoreVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ExamClassMapper {

    /**
     * 根据班级ID查询考试列表
     */
    @Select("SELECT ec.exam_id, " +
            "ec.exam_start_time, ec.exam_time, ec.create_time, ec.state, " +
            "e.exam_name, e.totalScore, e.totalTitle " +
            "FROM exam_clazz ec " +
            "LEFT JOIN exam e ON ec.exam_id = e.exam_id " +
            "WHERE ec.clazz_id = #{classId} " +
            "AND (ec.exam_start_time > NOW() " +
            "     OR (ec.exam_start_time <= NOW() " +
            "         AND DATE_ADD(ec.exam_start_time, INTERVAL ec.exam_time MINUTE) > NOW())) " +
            "ORDER BY " +
            "  CASE " +
            "    WHEN ec.exam_start_time <= NOW() AND DATE_ADD(ec.exam_start_time, INTERVAL ec.exam_time MINUTE) > NOW() THEN 1 " +
            "    ELSE 2 " +
            "  END, " +
            "  ec.exam_start_time ASC")
    List<ExamClazzVO> selectExamsByClassId(@Param("classId") Long classId);

    /**
     * 根据考试ID查询总分
     */
    @Select("SELECT totalScore " +
            "FROM exam " +
            "WHERE exam_id = #{examId}")
    Integer selectTotalScoreByExamId(@Param("examId") Long examId);

    /**
     * 查询学生考试成绩
     */
    @Select("SELECT score FROM student_exam " +
            "WHERE exam_clazz_id = #{examClazzId} AND student_id = #{studentId}")
    Integer selectStudentScore(@Param("examClazzId") Long examClazzId);

    /**
     * 查询考试详情
     */
    @Select("SELECT ec.exam_clazz_id,ec.exam_start_time ,ec.exam_time , e.exam_name, e.totalScore, er.score " +
            "FROM exam_clazz ec " +
            "LEFT JOIN exam e ON ec.exam_id = e.exam_id " +
            "LEFT JOIN exam_record er ON er.exam_id = ec.exam_id " +
            "WHERE ec.clazz_id = #{clazzId} AND er.student_id = #{studentId}")
    List<ExamScoreVO> selectExamDetail(@Param("clazzId") Long clazzId, @Param("studentId") Long studentId);
}