package com.czy.template.controller;

import com.czy.template.mapper.UserMapper;
import com.czy.template.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    UserMapper userMapper;

    //重定向到首页，为了防止别人看到请求头
    @RequestMapping("/userHomepage")
    public String userHomepage(HttpServletRequest req,
                           Model model) {
        model.addAttribute("username",req.getSession().getAttribute("username"));
        return "html/user/userHomepage";
    }

}
