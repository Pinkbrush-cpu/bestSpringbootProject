package com.czy.template.controller;

import com.czy.template.pojo.Exam;
import com.czy.template.service.ExamService;
import com.czy.template.util.JwtUtil;
import com.czy.template.util.Result;
import com.czy.template.view.dto.PublishExamDTO;
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
        Long id = jwtUtil.getUserFromRequest(request).getId();
        return examService.getExamList(id);
    }

    @PostMapping("/publishExamByUUID")
    public Result<Void> publishExamByUUID(@RequestBody PublishExamDTO dto,
                                          HttpServletRequest request) {
        Long id = jwtUtil.getUserFromRequest(request).getId();
        return examService.publishExamByUuid(dto,id);
    }



}