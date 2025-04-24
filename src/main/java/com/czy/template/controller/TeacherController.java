package com.czy.template.controller;

import com.czy.template.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TeacherController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/teacherHomepage")
    public String teacherHomepage(HttpServletRequest req,
                                  Model model) {
        model.addAttribute("username",req.getSession().getAttribute("username"));
        return "html/teacherHomepage";
    }
}
