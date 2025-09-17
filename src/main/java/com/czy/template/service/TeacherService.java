package com.czy.template.service;

import com.czy.template.dto.IdsDTO;
import com.czy.template.dto.PageRespDTO;
import com.czy.template.dto.PublicExamDTO;
import com.czy.template.dto.QuestionCreateDTO;
import com.czy.template.mapper.TeacherMapper;
import com.czy.template.pojo.Exam;
import com.czy.template.pojo.Question;
import com.czy.template.util.JwtUtil;
import com.czy.template.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TeacherService {

    @Autowired
    private final TeacherMapper teacherMapper;

    @Autowired
    JwtUtil jwtUtil;

    public Boolean create(QuestionCreateDTO dto) {
        Question q = new Question();
        BeanUtils.copyProperties(dto, q);
        // 从请求头解析 JWT 得到当前教师
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest();
        //使用jwtUtil方法拿到id
        int teacherId = jwtUtil.getUserFromRequest(request).getId();
        q.setCreateId(teacherId);
        try {
            q.setOptions(new ObjectMapper().writeValueAsString(dto.getOptions()));
        } catch (Exception e) {
            throw new RuntimeException("选项序列化失败", e);
        }
        return teacherMapper.createQuestion(q) == 1;
    }

    public Result<PageRespDTO<Question>> listTeacherQuestion(Map<String, Object> param, HttpServletRequest request) {
        /* 1. 解析用户 */
        long userId = jwtUtil.getUserFromRequest(request).getId();

        /* 2. 取参数并做类型转换 */
        int page = Integer.parseInt(String.valueOf(param.getOrDefault("page", 1)));
        int size = Integer.parseInt(String.valueOf(param.getOrDefault("size", 7)));
        String keyword = (String) param.get("keyword");

        long offset = (long) (page - 1) * size;

        /* 3. 查询总数 */
        long total = teacherMapper.countQuestionByKeyword(userId, keyword);

        /* 4. 查询分页数据 */
        List<Question> list = teacherMapper.selectQuestionByKeyword(userId, offset, size, keyword);

        /* 5. 清洗 options */
        list.forEach(q -> {
            if ("[, ]".equals(q.getOptions())) {
                q.setOptions(null);
            }
        });

        /* 6. 封装分页 */
        long pages = (total + size - 1) / size;
        PageRespDTO<Question> dto = new PageRespDTO<>(list, total, page, pages, size);

        return Result.ok(dto);
    }

    //删除题目实现
    public Boolean listDeleteQuestion(IdsDTO ids) {
        int i = 0;
        for(long qId : ids.getIds()){
            teacherMapper.deleteQuestionById(qId);
            i++;
        }
        if(i == ids.getIds().size()){
            return true;
        }
        return false;
    }

    public Result<List<Question>> selectQuestion(IdsDTO ids) {
        int i = 0;
        List<Question> data = new ArrayList<>();
        for(Long qId : ids.getIds()){
            if (qId == null) continue;
            data.add(teacherMapper.selectQuestion(qId));
            i++;
        }
        if(i == ids.getIds().size()){
            return Result.ok(data);
        }
        return Result.error("数据错误");
    }

    public Boolean updateQuestionService(QuestionCreateDTO dto,long qId){
        Question q = new Question();
        BeanUtils.copyProperties(dto, q);
        q.setqId((int)qId);
        try {
            q.setOptions(new ObjectMapper().writeValueAsString(dto.getOptions()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return teacherMapper.updateQuestionByqId(q);

    }

    public Boolean publishExamService(PublicExamDTO dto){
        Exam exam = new Exam();
        BeanUtils.copyProperties(dto, exam);
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest();
        //使用jwtUtil方法拿到id
        int teacherId = jwtUtil.getUserFromRequest(request).getId();
        exam.setCreate_id(teacherId);
        exam.setStatus("已发布");
        try {
            exam.setQuestionIds(new ObjectMapper().writeValueAsString(dto.getQuestionIds()));
            String questionIdsJson = new ObjectMapper().writeValueAsString(dto.getQuestionIds());
            System.out.println("写入数据库的 questionIds: " + questionIdsJson);
        } catch (Exception e) {
            throw new RuntimeException("选项序列化失败", e);
        }
        if(dto.getExamId() == 0)
            return teacherMapper.publishExam(exam) == 1;

        return teacherMapper.updateExamByqId(exam);
    }

}