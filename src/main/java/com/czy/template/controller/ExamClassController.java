package com.czy.template.controller;

import com.czy.template.pojo.ExamClazz;
import com.czy.template.service.ExamClassService;
import com.czy.template.util.Result;
import com.czy.template.view.vo.ExamClazzVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pink/examClass")
public class ExamClassController {

    @Autowired
    ExamClassService examClassService;

    @GetMapping("/viewClassExam/{classId}")
    public Result<List<ExamClazzVO>> getExamsByClass(@PathVariable Long classId) {
        try {
            List<ExamClazzVO> exams = examClassService.getExamsByClassId(classId);
            return Result.ok(exams);
        } catch (Exception e) {
            return Result.error("获取考试列表失败!");
        }
    }

    @GetMapping("recent/{examClazzId}")
    public Result<ExamClazz> getExamDetail(@PathVariable Long examClazzId) {
        try {
            ExamClazz exam = examClassService.getExamDetail(examClazzId);
            return Result.ok(exam);
        } catch (Exception e) {
            return Result.error("获取考试详情失败: " + e.getMessage());
        }
    }

    @PostMapping("/{examClazzId}/start")
    public Result startExam(@PathVariable Long examClazzId) {
        try {
            // 检查考试状态、权限等
            boolean canStart = examClassService.canStartExam(examClazzId);
            if (!canStart) {
                return Result.error("无法开始考试");
            }
            return Result.ok("考试开始");
        } catch (Exception e) {
            return Result.error("开始考试失败: " + e.getMessage());
        }
    }
}
