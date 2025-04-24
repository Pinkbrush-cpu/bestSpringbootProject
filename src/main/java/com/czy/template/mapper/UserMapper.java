package com.czy.template.mapper;

import com.czy.template.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


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

    //修改密码
    @Update("UPDATE user SET password = #{password} WHERE id = #{id}")
    int modifyPassword(@Param("password") String newPassword, int id);

    //查询全部用户
    @Select("SELECT * FROM user")
    List<User> selectAllUser();

    //根据id修改用户权限
    @Update("UPDATE user SET identity = #{identity} WHERE id = #{id}")
    int setAndCancel(User user);
}
