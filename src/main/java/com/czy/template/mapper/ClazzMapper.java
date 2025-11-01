package com.czy.template.mapper;

import com.czy.template.pojo.Clazz;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClazzMapper {

    //分页班级并且可以通过字段查询
    @Select({
            "<script>",
            "SELECT class_id, class_name, class_code, teacher_id, ",
            "       admission_year, student_max_count, class_state ",
            "FROM clazz ",
            "<where>",
            "  <if test='keyword != null and keyword != \"\"'>",
            "    AND (",
            "          class_name    LIKE CONCAT('%', #{keyword}, '%') ",
            "       OR class_code    LIKE CONCAT('%', #{keyword}, '%') ",
            "       OR admission_year LIKE CONCAT('%', #{keyword}, '%') ",
            "       OR class_state   LIKE CONCAT('%', #{keyword}, '%') ",
            "    )",
            "  </if>",
            "</where>",
            "ORDER BY admission_year, class_code ",
            "LIMIT #{offset}, #{size}",
            "</script>"
    })
    List<Clazz> selectClazzPage(@Param("offset") long offset,
                                @Param("size") int size,
                                @Param("keyword") String keyword);

    // 查询教师对应班级
    @Select({
            "<script>",
            "SELECT class_id, class_name, class_code, teacher_id, ",
            "       admission_year,student_max_count, class_state ",
            "FROM clazz ",
            "<where>",
            "   teacher_id = #{teacherId}",
            "   <if test='keyword != null and keyword != \"\"'>",
            "       AND (",
            "             class_name LIKE CONCAT('%', #{keyword}, '%') ",
            "          OR class_code LIKE CONCAT('%', #{keyword}, '%') ",
            "          OR admission_year LIKE CONCAT('%', #{keyword}, '%') ",
            "          OR class_state LIKE CONCAT('%', #{keyword}, '%') ",
            "       )",
            "   </if>",
            "</where>",
            "ORDER BY admission_year DESC, class_code ",
            "LIMIT #{offset}, #{size}",
            "</script>"
    })
    List<Clazz> selectClazzPageByTeacherId(@Param("offset") long offset,
                                           @Param("size") int size,
                                           @Param("keyword") String keyword,
                                           @Param("teacherId") Long teacherId);

    //根据clazzId查询Clazz
    @Select("SELECT * FROM clazz WHERE class_id = #{classId}")
    Clazz selectClazzById(@Param("classId")Long classId);

    //批量教师
    @Select({"SELECT realname FROM user WHERE identity = 2 AND id = #{tId} ",})
    String selectTeachers(@Param("tId") Long tId);

    //查询是否有相同班级码
    @Select("SELECT * FROM clazz WHERE class_code = #{classCode}")
    Clazz selectClazzByClassCode(@Param("classCode") String classCode);

    // 学生数
    @Select({"SELECT COUNT(*) FROM clazz_student WHERE clazz_id = #{clazzId}  "})
    int selectStuCount(@Param("clazzId") int clazzId);

    //总条数
    @Select({"<script>",
            "SELECT COUNT(*) FROM clazz ",
            "<where>",
            "  <if test='teacherId != null'>",
            "    teacher_id = #{teacherId}",
            "  </if>",
            "  <if test='keyword != null and keyword != \"\"'>",
            "    AND class_name LIKE CONCAT('%', #{keyword}, '%')",
            "  </if>",
            "</where>",
            "</script>"})
    int countClazz(@Param("teacherId") Long teacherId,@Param("keyword") String keyword);

    @Select({"SELECT id,realname,phone FROM user WHERE identity = 2 AND realname LIKE CONCAT('%', #{name}, '%') ORDER BY id ASC"})
    List<Map<String,String>> selectTeacherByName(@Param("name") String name);

//    @Insert("INSERT INTO clazz(class_name,class_code,teacher_id,admission_year) ")
//    Boolean insertClazz(Clazz clazz);

    @Insert("INSERT INTO clazz (class_id,class_name,class_code,teacher_id," +
            "admission_year,student_max_count,class_state) " +
            "VALUES (#{classId},#{className},#{classCode},#{teacherId}," +
            "#{admissionYear},#{studentMaxCount},#{classState})")
    int addClazz(Clazz clazz);

    @Update("UPDATE clazz SET class_name=#{className},class_code=#{classCode},teacher_id=#{teacherId}," +
            "admission_year=#{admissionYear},student_max_count=#{studentMaxCount},class_state=#{classState} " +
            "WHERE class_id=#{classId}")
    int updateClazz(Clazz cla);

    //删除班级
    @Delete("delete from clazz where class_id = #{classId}")
    int deleteClazz(Long clazzId);

    //根据教师id查看姓名
    @Select("SELECT realname FROM user WHERE id = #{id}")
    String selectTeacherName(Long id);

    @Select("SELECT clazz_id FROM clazz_student WHERE student_id = #{id} LIMIT #{offset}, #{size}")
    List<Long> selectByStudentId(@Param("offset") long offset,
                                 @Param("size") int size,
                                 @Param("keyword") String keyword,
                                 @Param("id") Long id);
}