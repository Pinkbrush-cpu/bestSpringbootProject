package com.czy.template.controller;

import com.czy.template.dto.IdsDTO;
import com.czy.template.dto.PageRespDTO;
import com.czy.template.dto.PublicExamDTO;
import com.czy.template.dto.QuestionCreateDTO;
import com.czy.template.mapper.TeacherMapper;
import com.czy.template.pojo.Exam;
import com.czy.template.pojo.Question;
import com.czy.template.service.TeacherService;
import com.czy.template.util.JwtUtil;
import com.czy.template.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/pink/educator")
public class TeacherController {

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    TeacherService teacherService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/createQuestion")
    public Result<String> createQuestion(@Validated @RequestBody QuestionCreateDTO dto) {
        if (dto != null && teacherService.create(dto)){
        // 返回 200 + 完整实体
            return Result.ok("题目保存成功");
        } else {
            return Result.error("保存失败");
        }
    }

    //所有题目
    @GetMapping("/teacherViewTopic")
    public Result<PageRespDTO<Question>> teacherAllTopic(@RequestParam Map<String, Object> param,
                                                         HttpServletRequest request) {
        // ① 把分页缺省值写进 Map（仅当请求未传时）
        param.putIfAbsent("page", 1);
        param.putIfAbsent("size", 8);

        // ② 直接扔给 Service，Controller 不再关心任何业务
        return teacherService.listTeacherQuestion(param, request);
    }

    //删除题目
    @DeleteMapping("/deleteQuestions")
    public Boolean deleteQuestions(@RequestBody IdsDTO ids) {

        return teacherService.listDeleteQuestion(ids);

    }

    //将选中的题目信息返回前端
    @PostMapping("/selectQuestion")
    public Result<List<Question>> selectQuestion(@RequestBody IdsDTO ids) {

        return teacherService.selectQuestion(ids);

    }


    @PutMapping("/{qId}/updateQuestion")
    public Result<Void> updateQuestion(@PathVariable Long qId,
                                       @RequestBody @Valid QuestionCreateDTO dto){
        if(teacherService.updateQuestionService(dto,qId))
            return Result.ok();

        return Result.error("更新失败，稍后重试");
    }

    //教师端查看考试内容
    @RequestMapping("/publishExam")
    public Result<Void> teacherPublicExam(@RequestBody @Valid PublicExamDTO dto) {

        if(dto.getExamId() == 0 && teacherService.publishExamService(dto)){
            return Result.ok();
        }

        return Result.error("发布失败");
    }

    //返回所有考试
    @GetMapping("/examList")
    public Result<PageRespDTO<PublicExamDTO>> examList(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "7") long size) {

        // 伪数据：实际替换成 MP/JPA 分页
        List<PublicExamDTO> records = List.of(
                new PublicExamDTO(142857, "2025 春季期中", List.of(38,40,37,45,36,43,46,39,47), 100, 20),
                new PublicExamDTO(142858, "2025 春季月考", List.of(36,41), 80, 15),
                new PublicExamDTO(142859, "2025 春季月考", List.of(36,41), 80, 15),
                new PublicExamDTO(142860, "2025 春季月考", List.of(36,41), 80, 15),
                new PublicExamDTO(142861, "2025 春季月考", List.of(36,41), 80, 15),
                new PublicExamDTO(142862, "2025 春季月考", List.of(36,41), 80, 15),
                new PublicExamDTO(142863, "2025 春季月考", List.of(36,41), 80, 15),
                new PublicExamDTO(142864, "2025 春季月考", List.of(36,41), 80, 15),
                new PublicExamDTO(142865, "2025 春季月考", List.of(36,41), 80, 15),
                new PublicExamDTO(142863, "2025 春季月考", List.of(36,41), 80, 15),
                new PublicExamDTO(142864, "2025 春季月考", List.of(36,41), 80, 15),
                new PublicExamDTO(142865, "2025 春季月考", List.of(36,41), 80, 15)
        );
        long total = records.size();                       // 数据库 count
        long pages = (total + size - 1) / size;
        PageRespDTO<PublicExamDTO> resp = new PageRespDTO<>(records, total, pages, page, size);
        return Result.ok(resp);
    }


}
