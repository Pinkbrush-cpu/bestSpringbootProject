package com.czy.template.service;

import com.czy.template.view.dto.IdsDTO;
import com.czy.template.view.vo.PageRespVO;
import com.czy.template.view.dto.PublicExamDTO;
import com.czy.template.view.dto.QuestionCreateDTO;
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

import java.util.*;

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
        Long teacherId = jwtUtil.getUserFromRequest(request).getId();
        q.setCreateId(teacherId);

        if (dto.getTitle() != null) {
            q.setTitle(wrapMixedText(dto.getTitle(), 40));
        }

        try {
            q.setOptions(new ObjectMapper().writeValueAsString(dto.getOptions()));
        } catch (Exception e) {
            throw new RuntimeException("选项序列化失败", e);
        }
        return teacherMapper.createQuestion(q) == 1;
    }


    private String wrapMixedText(String text, double lineWidth) {
        if (text == null || text.isEmpty()) return text;

        StringBuilder sb = new StringBuilder();
        double count = 0.0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            sb.append(c);

            // 遇到换行符时，重置计数
            if (c == '\n') {
                count = 0;
                continue;
            }

            // 判断字符宽度：中文/全角 1，英文/半角 0.5
            if (isFullWidth(c)) {
                count += 1.0;
            } else {
                count += 0.5;
            }

            // 达到行宽时处理
            if (count >= lineWidth) {
                int lastLineStart = sb.lastIndexOf("\n") + 1;
                String recent = sb.substring(lastLineStart);
                if (!recent.contains("\n")) {
                    sb.append("\n");
                }
                count = 0;
            }
        }
        return sb.toString();
    }


    private boolean isFullWidth(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.HANGUL_SYLLABLES
                || ub == Character.UnicodeBlock.HIRAGANA
                || ub == Character.UnicodeBlock.KATAKANA;
    }


    public Result<PageRespVO<Question>> listTeacherQuestion(Map<String, Object> param, HttpServletRequest request) {
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
        PageRespVO<Question> dto = new PageRespVO<>(list, total, page, pages, size);

        return Result.ok(dto);
    }

    //删除题目实现
    public Boolean listDeleteQuestion(IdsDTO ids) {
        int i = 0;
        for(long questionId : ids.getIds()){
            teacherMapper.deleteQuestionById(questionId);
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
        for(Long questionId : ids.getIds()){
            if (questionId == null) continue;
            data.add(teacherMapper.selectQuestion(questionId));
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
        q.setQuestionId((int)qId);
        try {
            q.setOptions(new ObjectMapper().writeValueAsString(dto.getOptions()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return teacherMapper.updateQuestionByqId(q);

    }

    //发布考试
    public Boolean publishExamService(PublicExamDTO dto){
        Exam exam = new Exam();
        BeanUtils.copyProperties(dto, exam);
        exam.setExamUuid(UUID.randomUUID().toString().replace("-", "").substring(0, 12));
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest();
        //使用jwtUtil方法拿到id
        Long teacherId = jwtUtil.getUserFromRequest(request).getId();
        exam.setCreateId(teacherId);
        exam.setStatus("已发布");
        try {
            exam.setQuestionIds(new ObjectMapper().writeValueAsString(dto.getQuestionIds()));
        } catch (Exception e) {
            throw new RuntimeException("选项序列化失败", e);
        }
        if(dto.getExamId() == 0)
            return teacherMapper.publishExam(exam) == 1;

        return teacherMapper.updateExamByqId(exam);
    }

    //分页返回发布的考试
    public Result<PageRespVO<PublicExamDTO>> examListService(long page,long size, HttpServletRequest request){
        long userId = jwtUtil.getUserFromRequest(request).getId();
        long offset =(page - 1) * size;

        List<Exam> exams = teacherMapper.selectExamById(userId, offset, size);

        List<PublicExamDTO> records = exams.stream().map(exam -> {
            PublicExamDTO dto = new PublicExamDTO();
            dto.setExamId(exam.getExamId());
            dto.setTitle(exam.getExamName());
            dto.setTotalScore(exam.getTotalScore());
            dto.setTotalTitle(exam.getTotalTitle());

            // 处理 questionIds
            if (exam.getQuestionIds() != null && !exam.getQuestionIds().isEmpty()) {
                String cleaned = exam.getQuestionIds()
                        .replace("[", "")   // 去掉左中括号
                        .replace("]", "");  // 去掉右中括号

                List<Integer> ids = Arrays.stream(cleaned.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())   // 过滤空字符串
                        .map(Integer::parseInt)
                        .toList();

                dto.setQuestionIds(ids);
            } else {
                dto.setQuestionIds(new ArrayList<>());
            }

            return dto;
        }).toList();

        long total = records.size();
        long pages = (total + size - 1) / size;
        PageRespVO<PublicExamDTO> resp = new PageRespVO<>(records, total, pages, page, size);
        return Result.ok(resp);
    }


}