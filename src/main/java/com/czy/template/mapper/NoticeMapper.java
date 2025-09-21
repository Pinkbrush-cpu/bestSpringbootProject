package com.czy.template.mapper;

import com.czy.template.pojo.Notice;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoticeMapper {
    @Insert("INSERT INTO notice(title, content, target, create_time) " +
            "VALUES(#{title}, #{content}, #{target}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Notice notice);

    @Select("SELECT n.* FROM notice n " +
            "JOIN user_notice_status s ON n.n_id = s.notice_id " +
            "WHERE s.user_id = #{userId} AND s.status = 0")
    List<Notice> findUnreadByUserId(@Param("userId") Long userId);

    //给每个用户插入一条未读消息
    @Insert("INSERT INTO user_notice_status(notice_id,user_id, status) " +
            "VALUES(#{noticeId}, #{userId}, 0)")
    void insertStatus(@Param("userId") Long userId,
                      @Param("noticeId") Long noticeId);
}
