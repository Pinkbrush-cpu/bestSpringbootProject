package com.czy.template.service;

import com.czy.template.mapper.ExamClassMapper;
import com.czy.template.pojo.ExamClazz;
import com.czy.template.view.vo.ExamClazzVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExamClassService {

    @Autowired
    private ExamClassMapper examClassMapper;

    public List<ExamClazzVO> getExamsByClassId(Long classId) {
        List<ExamClazzVO> exams = examClassMapper.selectExamsByClassId(classId);

        // 为每个考试设置扩展信息
        for (ExamClazzVO exam : exams) {

            // 设置总分
            Integer totalScore = examClassMapper.selectTotalScoreByExamId(exam.getExamId());
            exam.setTotalScore(totalScore);

        }

        return exams;
    }

    public ExamClazz getExamDetail(Long examClazzId) {
        return examClassMapper.selectExamDetail(examClazzId);
    }

    public boolean canStartExam(Long examClazzId) {
        ExamClazz exam = examClassMapper.selectExamDetail(examClazzId);
        if (exam == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = exam.getExamStartTime();
        LocalDateTime endTime = startTime.plusMinutes(exam.getExamTime());

        // 检查是否在考试时间内
        return now.isAfter(startTime) && now.isBefore(endTime);
    }

}
