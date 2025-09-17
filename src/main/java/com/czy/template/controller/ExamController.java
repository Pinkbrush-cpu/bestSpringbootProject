package com.czy.template.controller;

import com.czy.template.service.ExamService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pink/exam")
public class ExamController {

    @Resource
    private ExamService examService;

    }