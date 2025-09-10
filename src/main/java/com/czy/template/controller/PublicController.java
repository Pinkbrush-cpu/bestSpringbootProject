package com.czy.template.controller;

import com.czy.template.dto.ChangePwdDTO;
import com.czy.template.dto.ModifyInformationDTO;
import com.czy.template.mapper.UserMapper;
import com.czy.template.pojo.User;
import com.czy.template.util.JwtUtil;
import com.czy.template.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pink/{identity}")
public class PublicController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtUtil jwtUtil;

    //修改个人信息页面请求
    @PostMapping("/modifyInformation")
    public Result<Map<String, Object>> modifyInformation(@RequestBody ModifyInformationDTO dto,
                                                         HttpServletRequest req) {
        User user = jwtUtil.getUserFromRequest(req);

        if (user == null) {
            return Result.error("未登录");
        }
        User ouser = userMapper.findByUsername(dto.getUsername());
        if(ouser != null && !user.getUsername().equals(ouser.getUsername())){
            return Result.error("用户名已存在");
        }

        // 构建新对象（只改允许字段）
        User newUser = new User(
                user.getId(),
                dto.getRealname(),
                dto.getUsername(),
                user.getPassword(),
                dto.getPhone(),
                dto.getEmail(),
                dto.getGender(),
                dto.getAddress(),
                user.getIdentity()
        );

        int row = userMapper.modifyPersonalInformation(newUser);
        if (row == 1) {
            Map<String, Object> data = new HashMap<>();
            data.put("user", newUser);
            return Result.ok(data);
        }
        return Result.error("保存失败");
    }

    @PostMapping("/changePassword")
    public Result<String> changePassword(@RequestBody ChangePwdDTO dto,
                                         HttpServletRequest req) {
        // 1. 拿当前登录人
        User user = jwtUtil.getUserFromRequest(req);

        // 2. 校验原密码
        if (user == null || !user.getPassword().equals(dto.getPassword())) {
            return Result.error("原密码错误");
        }

        // 3. 更新密码
        int row = userMapper.changePassword(dto.getNewPassword(),user.getId());
        return row == 1
                ? Result.ok("密码已修改，请重新登录")
                : Result.error("更新失败");
    }
}
