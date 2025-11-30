package com.czy.template.service;

import com.czy.template.mapper.ExamClassMapper;
import com.czy.template.util.JwtUtil;
import com.czy.template.view.vo.ExamClazzVO;
import com.czy.template.view.vo.ExamScoreVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExamClassService {

    @Autowired
    JwtUtil jwtUtil;

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

    public List<ExamScoreVO> getExamDetail(Long clazzId, HttpServletRequest request) {
        Long id = jwtUtil.getUserFromRequest(request).getId();
        System.out.println(examClassMapper.selectExamDetail(clazzId,id));
        return examClassMapper.selectExamDetail(clazzId,id);
    }

//    public boolean canStartExam(Long clazzId, HttpServletRequest request) {
//        Long id = jwtUtil.getUserFromRequest(request).getId();
//        ExamScoreVO examScoreVO = examClassMapper.selectExamDetail(clazzId,id);
//        if (examScoreVO == null) {
//            return false;
//        }
//
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime startTime = examScoreVO.getExamStartTime();
//        LocalDateTime endTime = startTime.plusMinutes(examScoreVO.getExamTime());

        // 检查是否在考试时间内
        //return now.isAfter(startTime) && now.isBefore(endTime);
    //}

}
