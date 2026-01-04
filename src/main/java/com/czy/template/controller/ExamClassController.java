package com.czy.template.controller;

import com.czy.template.service.ExamClassService;
import com.czy.template.util.Result;
import com.czy.template.view.vo.ExamClazzVO;
import com.czy.template.view.vo.ExamScoreVO;
import com.czy.template.view.vo.TakeExamVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    public Result<List<ExamScoreVO>> getExamDetail(@PathVariable Long examClazzId, HttpServletRequest request) {
        try {
            List<ExamScoreVO> exam = examClassService.getExamDetail(examClazzId,request);
            return Result.ok(exam);
        } catch (Exception e) {
            return Result.error("获取考试详情失败: " + e.getMessage());
        }
    }

    //验证考试
    @PostMapping("validateExam")
    public Result<Void> validateExam(
            @RequestParam Long examClazzId,
            @RequestParam Long classId,
            @RequestParam Date timestamp
    ) {
        System.out.println("验证考试: examClazzId=" + examClazzId + ", classId=" + classId + ", timestamp=" + timestamp);
        return Result.ok();
    }

//    @PostMapping("/{examClazzId}/start")
//    public Result startExam(@PathVariable Long examClazzId, HttpServletRequest request) {
//        try {
//            // 检查考试状态、权限等
//            boolean canStart = examClassService.canStartExam(examClazzId,request);
//            if (!canStart) {
//                return Result.error("无法开始考试");
//            }
//            return Result.ok("考试开始");
//        } catch (Exception e) {
//            return Result.error("开始考试失败: " + e.getMessage());
//        }
//    }


    //获取学生开始考试的数据
    @GetMapping("/take/{examClazzId}")
    public Result<TakeExamVO> takeExam(@PathVariable Long examClazzId) {
        return examClassService.takeExam(examClazzId);
    }


}
