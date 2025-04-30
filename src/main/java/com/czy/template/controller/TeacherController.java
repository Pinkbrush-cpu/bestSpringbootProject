package com.czy.template.controller;

import com.czy.template.mapper.TeacherMapper;
import com.czy.template.mapper.UserMapper;
import com.czy.template.pojo.Question;
import com.czy.template.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TeacherController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @RequestMapping("/teacherHomepage")
    public String teacherHomepage(HttpServletRequest req,
                                  Model model) {
        model.addAttribute("username",req.getSession().getAttribute("username"));
        return "html/teacher/teacherHomepage";
    }

    @RequestMapping("/teacherCreateQuestion")
    public String teacherCreateQuestion(HttpServletRequest req,
                                  Model model) {
        model.addAttribute("username",req.getSession().getAttribute("username"));
        return "html/teacher/teacherCreateQuestion";
    }

    @RequestMapping("/createQuestion")
    public String createQuestion(int score,
                                 String type,
                                 String title,
                                 @RequestParam(required = false) List<String> options,
                                 String answer,
                                 HttpServletRequest req,
                                 Model model) {
        model.addAttribute("username",req.getSession().getAttribute("username"));
        Question question;
        if(options.equals("[,]"))
            question = new Question(0, (Integer) req.getSession().getAttribute("userId"), type, title, "", answer, "", score);
        else {
            question = new Question(0, (Integer) req.getSession().getAttribute("userId"), type, title, options.toString(), answer, "", score);
        }
        teacherMapper.createQuestion(question);
        return "redirect:/teacherViewTopic";
    }

    @RequestMapping("/teacherViewTopic")
    public String teacherViewTopic(HttpServletRequest req,
                                   Model model) {
        List<Question> questions = teacherMapper.selectAllQuestion((Integer) req.getSession().getAttribute("userId"));
        System.out.println(questions.toString());
        model.addAttribute("questions",questions);
        model.addAttribute("username",req.getSession().getAttribute("username"));
        return "html/teacher/teacherViewTopic";
    }

}
