package com.czy.template.mapper;

import com.czy.template.pojo.Exam;
import com.czy.template.pojo.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {

    //插入创建的题目
    @Insert("INSERT INTO question(create_id,type,title,options,answer,score,answer_text) VALUES (#{createId},#{type},#{title},#{options},#{answer},#{score},#{answerText})")
    int createQuestion(Question question);

    //根据题目id搜索题目
    @Select("SELECT * FROM question where question_id = #{questionId}")
    Question selectQuestion(long questionId);

    //根据教师id搜索所有创建的题目
    @Select("SELECT * FROM question where create_id = #{create_id}")
    List<Question> selectAllQuestion(int create_id);

    //根据教师id搜索考试卷
    @Select("SELECT * FROM exam where create_id = #{create_id}")
    List<Exam> selectAllExam(int create_id);

    //搜索条件查询
    @Select("SELECT * FROM question " +
            "WHERE create_id = #{userId} " +
            "  AND (#{keyword} IS NULL OR title LIKE CONCAT('%', #{keyword}, '%')) " +
            "ORDER BY type " +
            "LIMIT #{offset}, #{size}")
    List<Question> selectQuestionByKeyword(@Param("userId") long userId,
                                           @Param("offset") long offset,
                                           @Param("size") int size,
                                           @Param("keyword") String keyword);
    /* 总条数 */
    @Select("SELECT COUNT(*) FROM question " +
            "WHERE create_id = #{userId} " +
            "  AND (#{keyword} IS NULL OR title LIKE CONCAT('%', #{keyword}, '%'))")
    long countQuestionByKeyword(@Param("userId") long userId,
                                @Param("keyword") String keyword);

    //根据传入的id数组删除题目
    @Delete("delete from question where question_id = #{questionId}")
    int deleteQuestionById(@Param("questionId") long questionId);

    //根据qId更新题目
    @Update("update question set title = #{title}, options = #{options}, answer_text = #{answerText}, answer = #{answer}, score = #{score} where question_id = #{questionId}")
    boolean updateQuestionByqId(Question question);

    @Insert("INSERT INTO exam(create_id,exam_name,status,questionIds,totalScore,totalTitle,exam_uuid) VALUES (#{create_id},#{examName},#{status},#{questionIds},#{totalScore},#{totalTitle},#{examUuid})")
    int publishExam(Exam exam);

    @Update("update exam set exam_name = #{exam_name}, status = #{status}, questionIds = #{questionIds}, totalScore = #{totalScore}, totalTitle = #{totalTitle} where exam_id = #{exam_id}")
    boolean updateExamByqId(Exam exam);

    //查看共有多少考试
    @Select("SELECT COUNT(*) FROM exam " +
            "WHERE create_id = #{userId} ")
    long countExamByKeyword(@Param("userId") long userId);

    //分页查询考试
    @Select("SELECT * FROM exam " +
            "WHERE create_id = #{userId} " +
            "LIMIT #{offset}, #{size}")
    List<Exam> selectExamById(@Param("userId") long userId,
                                       @Param("offset") long offset,
                                       @Param("size") long size);
}
