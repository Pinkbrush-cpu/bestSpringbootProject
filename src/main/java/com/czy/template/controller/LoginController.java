package com.czy.template.controller;

import com.czy.template.mapper.UserMapper;
import com.czy.template.pojo.User;
import com.czy.template.util.JwtUtil;
import com.czy.template.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pink")
public class LoginController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtUtil jwtUtil;

    /* -------------------- 登录 -------------------- */
    @PostMapping("/dologin")
    public Result<Map<String, Object>> loginIn(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        if (username == null || password == null) {
            return Result.error("用户名和密码不能为空");
        }

        User user = userMapper.findByUsername(username);
        if (user == null) {
            return Result.error("用户名不存在");
        }
        if (!user.getPassword().equals(password)) {
            return Result.error("密码错误");
        }

        // 权限路由
        String redirectUrl;
        switch (user.getIdentity()) {
            case 1:  redirectUrl = "/studentHomepage";  break;
            case 2:  redirectUrl = "/teacherHomepage"; break;
            case 10: redirectUrl = "/managerHomepage"; break;
            default: return Result.error("用户权限错误，请联系管理员");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getIdentity());

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("redirectUrl", redirectUrl);
        data.put("user", user);

        return Result.ok("登录成功", data);
    }

    /* -------------------- 注册 -------------------- */
    @PostMapping("/doregister")
    public Result<Void> register(@RequestBody Map<String, String> requestData) {
        String username = requestData.get("username");
        String password = requestData.get("password");
        String phone   = requestData.get("phone");
        String email   = requestData.get("email");

        if (userMapper.findByUsername(username) != null) {
            return Result.error("用户名重复！");
        }

        User newUser = new User(0, "未填写", username, password, phone, email, '空', "未填写", 1);
        int row = userMapper.registerUser(newUser);
        return row == 1 ? Result.ok() : Result.error("注册失败！");
    }
}