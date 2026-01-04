package com.czy.template.service;

import com.czy.template.mapper.ExamClassMapper;
import com.czy.template.mapper.ExamMapper;
import com.czy.template.mapper.TeacherMapper;
import com.czy.template.pojo.Exam;
import com.czy.template.pojo.ExamClazz;
import com.czy.template.pojo.Question;
import com.czy.template.util.JwtUtil;
import com.czy.template.util.Result;
import com.czy.template.view.vo.ExamClazzVO;
import com.czy.template.view.vo.ExamScoreVO;
import com.czy.template.view.vo.TakeExamVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExamClassService {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private ExamClassMapper examClassMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private TeacherMapper teacherMapper;

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

    public Result<TakeExamVO> takeExam(Long examClazzId) {
        ExamClazz examClazz = examClassMapper.selectExamClazz(examClazzId);
        Exam exam = examMapper.getExamByExamId(examClazz.getExamId());
        if(exam != null && exam.getQuestionIds() != null) {
            ArrayList<Question> questions = new ArrayList<>();
            Question question;
            String questionIds = exam.getQuestionIds();
            String cleaned = questionIds.substring(1, questionIds.length() - 1);
            String[] parts = cleaned.split(",");

            for (String part : parts) {
                if ((question = teacherMapper.selectQuestion(Long.parseLong(part))) != null) {
                    questions.add(question);
                }
            }

            TakeExamVO takeExamVO = new TakeExamVO();
            takeExamVO.setExamId(examClazz.getExamId());
            takeExamVO.setExamName(exam.getExamName());
            takeExamVO.setTotalScore(exam.getTotalScore());
            takeExamVO.setExamTime(examClazz.getExamTime());
            takeExamVO.setExamStartTime(examClazz.getExamStartTime());
            takeExamVO.setQuestions(questions);

            return Result.ok(takeExamVO);
        }
        return Result.error("获取考试失败");
    }
}
