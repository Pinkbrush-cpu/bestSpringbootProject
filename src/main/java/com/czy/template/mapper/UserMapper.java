package com.czy.template.mapper;

import com.czy.template.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);

    @Insert("INSERT INTO user(username,password,phone,email) VALUES (#{username}, #{password},#{phone},#{email})")
    int registerUser(User user);
}
