package com.czy.template.controller;

import com.czy.template.mapper.UserMapper;
import com.czy.template.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LoginController {

    @Autowired
    UserMapper userMapper;

    // 访问根路径时返回 index.html，可以使用户直接访问登录页面
    @RequestMapping("/")
    public String index() {
        return "html/index";
    }

    //登录请求
    @PostMapping("/dologin")
    public String loginIn(@RequestParam String username,
                          @RequestParam String password,
                          HttpServletRequest req){
        User user = userMapper.findByUsername(username);
        if(user != null && user.getPassword().equals(password)){
            req.getSession().setAttribute("user",user);
            req.getSession().setAttribute("userId",user.getId());
            req.getSession().setAttribute("username",user.getUsername());
            if(user.getIdentity() == 10){
                return "redirect:/managerHomepage";
            } else if(user.getIdentity() == 2){
                return "redirect:/teacherHomepage";
            }
            return "redirect:/homepage";
        }

        return "html/index";
    }

    //访问注册页面
    @RequestMapping("/register")
    public String register() {
        return "html/register";
    }


    //注册页面提交表单时发送的请求
    @PostMapping("/doregister")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           @RequestParam String phone,
                           @RequestParam String email,
                           RedirectAttributes reda){
        if(username.length() > 12){
            reda.addFlashAttribute("errorMessage","用户名超过最大长度！");
            return "redirect:/register";
        }

        if(!password.equals(confirmPassword)){
            reda.addFlashAttribute("errorMessage","两次密码不相同！");
            return "redirect:/register";
        }

        User user = userMapper.findByUsername(username);
        if(user == null){
            User newUser = new User(0,"未填写",username, password, phone, email,'空',"未填写",1);
            int u = userMapper.registerUser(newUser);
            if(u == 1) {
                return "redirect:/";
            } else {
                reda.addFlashAttribute("errorMessage","注册失败！");
                return "redirect:/register";
            }
        } else {
            reda.addFlashAttribute("errorMessage","用户名重复！");
            return "redirect:/register";
        }
    }


}
