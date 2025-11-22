package com.czy.template.controller;

import com.czy.template.mapper.ExamMapper;
import com.czy.template.pojo.Exam;
import com.czy.template.pojo.User;
import com.czy.template.service.ExamService;
import com.czy.template.util.JwtUtil;
import com.czy.template.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pink/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/viewExams")
    public Result<List<Exam>> getExamList(HttpServletRequest request) {
        User teacher = jwtUtil.getUserFromRequest(request);
        return examService.getExamList(teacher.getId());
    }

    public Result<Void> publishExamByUUID(String uuid, HttpServletRequest request) {
        Long id = jwtUtil.getUserFromRequest(request).getId();
        return examService.publishExamByUuid(uuid,id);
    }

}