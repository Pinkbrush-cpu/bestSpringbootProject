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
    @Select("SELECT * FROM question where q_id = #{q_id}")
    Question selectQuestion(long q_id);

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
    @Delete("delete from question where q_id = #{qId}")
    int deleteQuestionById(@Param("qId") long qId);

    //根据qId更新题目
    @Update("update question set title = #{title}, options = #{options}, answer_text = #{answerText}, answer = #{answer}, score = #{score} where q_id = #{qId}")
    boolean updateQuestionByqId(Question question);

    @Insert("INSERT INTO exam(create_id,title,status,questionIds,totalScore,totalTitles) VALUES (#{create_id},#{title},#{status},#{questionIds},#{totalScore},#{totalTitles})")
    int publishExam(Exam exam);

    @Update("update exam set title = #{title}, status = #{status}, questionIds = #{questionIds}, totalScore = #{totalScore}, totalTitles = #{totalTitles} where exam_id = #{exam_id}")
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
