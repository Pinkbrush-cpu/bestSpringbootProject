package com.czy.template.mapper;

import com.czy.template.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface UserMapper {
    //根据用户名搜索用户
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);

    //注册用户
    @Insert("INSERT INTO user(realname,username,password,phone,email,gender,address) VALUES (#{realname},#{username}, #{password},#{phone},#{email},#{gender},#{address})")
    int registerUser(User user);

    //修改个人信息
    @Update("UPDATE user SET realname = #{realname}, phone = #{phone}, email = #{email}, gender = #{gender}, address = #{address} WHERE id = #{id}")
    int modifyPersonalInformation(User user);

    @Update("UPDATE user SET password = #{password} WHERE id = #{id}")
    int modifyPassword(String newPassword, int id);
}
