package com.czy.template.controller;

import com.czy.template.mapper.UserMapper;
import com.czy.template.pojo.User;
import com.czy.template.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/pink")
public class LoginController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtUtil jwtUtil;

    //登录请求
    @PostMapping("/dologin")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> loginIn(
            @RequestBody Map<String, String> loginRequest) {

        Map<String, Object> response = new HashMap<>();
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        if (username == null || password == null) {
            response.put("success", false);
            response.put("message", "用户名和密码不能为空");
            return ResponseEntity.badRequest().body(response);
        }

        User user = userMapper.findByUsername(username);
        if (user == null) {
            response.put("success", false);
            response.put("message", "用户名不存在");
            return ResponseEntity.ok(response);
        }

        if (!user.getPassword().equals(password)) {
            response.put("success", false);
            response.put("message", "密码错误");
            return ResponseEntity.ok(response);
        }

        if(user.getIdentity() == 1) {
            response.put("redirectUrl","/studentHomepage");
        } else if (user.getIdentity() == 2) {
            response.put("redirectUrl","/teacherHomepage");
        } else if (user.getIdentity() == 10) {
            response.put("redirectUrl","/managerHomepage");
        } else {
            response.put("success", false);
            response.put("message", "用户权限错误，请联系管理员");
            return ResponseEntity.ok(response);
        }

        // 生成 JWT
        String token = jwtUtil.generateToken(user.getUsername(), user.getIdentity());

        // 构造响应，不再设置 session
        response.put("success", true);
        response.put("message", "登录成功");
        response.put("token", token); // ← 返回给前端
        response.put("user", user);

        return ResponseEntity.ok(response);
    }

    //注册页面提交表单时发送的请求
    @RequestMapping("/doregister")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> requestData) {
        Map<String, Object> response = new HashMap<>();

        String username = requestData.get("username");
        String password = requestData.get("password");
        String phone = requestData.get("phone");
        String email = requestData.get("email");

        User user = userMapper.findByUsername(username);
        if(user == null){
            User newUser = new User(0,"未填写",username, password, phone, email,'空',"未填写",1);
            int u = userMapper.registerUser(newUser);
            if(u == 1) {
                response.put("success", true);
                response.put("message", "注册成功");
            } else {
                response.put("success", false);
                response.put("errorMessage", "注册失败！");
            }
        } else {
            response.put("success", false);
            response.put("errorMessage", "用户名重复！");
        }

        return ResponseEntity.ok(response);
    }
}
