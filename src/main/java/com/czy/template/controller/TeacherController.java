package com.czy.template.controller;

import com.czy.template.view.dto.IdsDTO;
import com.czy.template.view.vo.PageRespVO;
import com.czy.template.view.dto.PublicExamDTO;
import com.czy.template.view.dto.QuestionCreateDTO;
import com.czy.template.pojo.Question;
import com.czy.template.service.TeacherService;
import com.czy.template.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/pink/educator")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

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
    public Result<PageRespVO<Question>> teacherAllTopic(@RequestParam Map<String, Object> param,
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


    //修改题目
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
    public Result<PageRespVO<PublicExamDTO>> examList(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "12") long size,
            HttpServletRequest request) {


        return teacherService.examListService(page, size, request);
    }


}
