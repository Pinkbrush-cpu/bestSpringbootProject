package com.czy.template.service;

import com.czy.template.mapper.ExamMapper;
import com.czy.template.pojo.Exam;
import com.czy.template.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {

    @Autowired
    ExamMapper examMapper;


    public Result<List<Exam>> getExamList(Long id) {
        List<Exam> exam = examMapper.getExam(id);
        if(exam != null) {
            return Result.ok(exam);
        }
        return Result.error("网络连接出现问题，请稍后重试！");
    }

    public Result<Void> publishExamByUuid(String uuid, Long id) {
        return Result.ok();
    }
}
