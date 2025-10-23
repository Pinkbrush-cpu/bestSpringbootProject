package com.czy.template.mapper;

import com.czy.template.view.dto.ModifyInformationDTO;
import com.czy.template.pojo.User;
import com.czy.template.view.vo.UserVO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;


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
    int changePassword(@Param("password") String newPassword, int id);

    //总条数
    @Select({"<script>",
            "SELECT COUNT(*) FROM user",
            "<where>",
            "  <if test='onlyOne'> id > 1 </if>",
            "  <if test='keyword != null and keyword != \"\"'>",
            "    AND (username LIKE CONCAT('%',#{keyword},'%')",
            "         OR realname LIKE CONCAT('%',#{keyword},'%')",
            "         OR phone   LIKE CONCAT('%',#{keyword},'%'))",
            "  </if>",
            "</where>",
            "</script>"})
    Long countByKeyword(@Param("keyword") String keyword,
                        @Param("onlyOne") boolean onlyOne);

    //当前页数据
    @Select({"<script>",
            "SELECT id,username,email,phone,identity FROM user",
            "<where>",
            "  <if test='onlyOne'> id > 1 </if>",
            "  <if test='keyword != null and keyword != \"\"'>",
            "    AND (username LIKE CONCAT('%',#{keyword},'%')",
            "         OR realname LIKE CONCAT('%',#{keyword},'%')",
            "         OR phone   LIKE CONCAT('%',#{keyword},'%'))",
            "  </if>",
            "</where>",
            "ORDER BY identity DESC, id DESC",
            "LIMIT #{offset}, #{size}",
            "</script>"})
    List<UserVO> selectPageByKeyword(@Param("offset") long offset,
                                     @Param("size") int size,
                                     @Param("keyword") String keyword,
                                     @Param("onlyOne") boolean onlyOne);

    //查询全部用户
    @Select("SELECT * FROM user")
    List<User> selectAllUser();

    //根据id查询用户
    @Select("SELECT username, realname, phone, email, gender, address " +
            "FROM user WHERE id = #{userId}")
    Optional<ModifyInformationDTO> selectUserById(Long userId);

    //根据id修改用户权限
    @Update("UPDATE user SET identity = #{identity} WHERE id = #{id}")
    int setAndCancel(User user);

    //查找身份为学生或老师的用户
    @Select("SELECT id FROM user " +
            "WHERE identity IN (1,2) " +
            "AND (#{identity} = 0 OR identity = #{identity})")
    List<Long> findUserIdsByRole(@Param("identity") int identity);



}
