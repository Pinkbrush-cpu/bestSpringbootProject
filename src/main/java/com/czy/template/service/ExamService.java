package com.czy.template.service;

import com.czy.template.mapper.ExamMapper;
import com.czy.template.pojo.Exam;
import com.czy.template.pojo.ExamClazz;
import com.czy.template.util.Result;
import com.czy.template.view.dto.PublishExamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Result<Void> publishExamByUuid(PublishExamDTO dto, Long id) {
        ExamClazz examClazz = new ExamClazz();
        examClazz.setExamClazzId(0L);
        examClazz.setClazzId(dto.getClazzId());
        examClazz.setExamId(dto.getExamId());
        examClazz.setCreateId(id);
        examClazz.setExamStartTime(dto.getExamStartTime());
        examClazz.setExamTime(dto.getExamTime());
        examClazz.setCreateTime(LocalDateTime.now());
        examClazz.setState("未开始");

        if(examMapper.publishExamByUuid(examClazz)) {
            System.out.println(123);
            return Result.ok();
        }

        return Result.error("发布失败，请稍后重试！");
    }
}
