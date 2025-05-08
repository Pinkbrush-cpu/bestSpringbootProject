package com.czy.template.controller;

import com.czy.template.mapper.TeacherMapper;
import com.czy.template.mapper.UserMapper;
import com.czy.template.pojo.Exam;
import com.czy.template.pojo.Question;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Set<Integer> addedQuestionIds = (Set<Integer>) model.getAttribute("addedQuestionIds");
        if (addedQuestionIds == null) {
            addedQuestionIds = new HashSet<>();
            addedQuestionIds.add(0);
        }

        List<Question> questions = teacherMapper.selectAllQuestion((Integer) req.getSession().getAttribute("userId"));
        for(Question question : questions){
            if(question.getOptions().equals("[, ]")){
                question.setOptions(null);
            }
        }
        model.addAttribute("questions",questions);
        model.addAttribute("username",req.getSession().getAttribute("username"));
        model.addAttribute("addedQuestionIds",addedQuestionIds);
        return "html/teacher/teacherViewTopic";
    }

    //教师端查看考试内容
    @RequestMapping("/teacherAddExam")
    public String teacherAddExam(HttpServletRequest req,
                                   Model model) {
        model.addAttribute("username",req.getSession().getAttribute("username"));
        Exam exam = (Exam) model.getAttribute("exam");
        if(exam == null){
            exam = new Exam();
            exam.setQuestions(new ArrayList<>());
        }

        model.addAttribute("exam",exam);
        model.addAttribute("totalScore",exam.getTotalScore());
        model.addAttribute("totalTitle",exam.getTotalTitle());
        return "html/teacher/teacherAddExam";
    }

    //添加到试卷|取消添加
    @PostMapping("/addQuestion")
    public String addQuestion(@RequestParam int questionId,
                              @RequestParam String action,
                              Model model) {
        // 获取当前已添加的题目 ID 集合
        Set<Integer> addedQuestionIds = (Set<Integer>) model.getAttribute("addedQuestionIds");
        if (addedQuestionIds == null) {
            addedQuestionIds = new HashSet<>();
        }

        Exam exam = (Exam) model.getAttribute("exam");
        if(exam == null){
            exam = new Exam();
            exam.setQuestions(new ArrayList<>());
        }

        // 根据 action 参数决定是添加还是移除题目
        if ("add".equals(action)) {
            addedQuestionIds.add(questionId);
            exam.getQuestions().add(teacherMapper.selectQuestion(questionId));
        } else if ("remove".equals(action)) {
            addedQuestionIds.remove(questionId);
            exam.getQuestions().remove(teacherMapper.selectQuestion(questionId));
        }

        // 将更新后的集合放回 model
        model.addAttribute("exam",exam);
        model.addAttribute("totalScore",exam.getTotalScore());
        model.addAttribute("totalTitle",exam.getTotalTitle());
        model.addAttribute("addedQuestionIds", addedQuestionIds);

        // 重新加载页面
        return "redirect:/teacherViewTopic";
    }

    //删除所有已添加题目
    @RequestMapping("/deleteAllItems")
    public String deleteAllItems(HttpServletRequest req,
                                 Model model) {
        model.addAttribute("username",req.getSession().getAttribute("username"));
        Exam exam = new Exam();
        exam.setQuestions(new ArrayList<Question>());
        model.addAttribute("exam",exam);
        model.addAttribute("totalScore",0);
        model.addAttribute("totalTitle",0);
        return "html/teacher/teacherAddExam";
    }
}
