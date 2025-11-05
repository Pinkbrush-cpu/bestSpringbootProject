package com.czy.template.controller;

import com.czy.template.mapper.ClazzMapper;
import com.czy.template.view.dto.ApproveDTO;
import com.czy.template.view.vo.PageRespVO;
import com.czy.template.pojo.Clazz;
import com.czy.template.pojo.User;
import com.czy.template.service.ClazzService;
import com.czy.template.util.Result;
import com.czy.template.view.vo.ClazzVO;
import com.czy.template.view.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pink/class")
public class ClassController {

    @Autowired
    ClazzService clazzService;

    @Autowired
    ClazzMapper clazzMapper;

    //管理员创建班级Controller
    @PostMapping("/addClazz")
    public Result<Void> addClazz(@RequestBody Clazz cls){

        if (clazzMapper.selectClazzByClassCode(cls.getClassCode()) != null)
            return Result.error("班级码重复");

        if(clazzMapper.addClazz(cls) == 1)
            return Result.ok();
        return Result.error("新增失败");
    }

    //更新班级
    @PutMapping("/updateClazz/{classId}")
    public Result<Void> updateClazz(@RequestBody Clazz cls){
        if(clazzMapper.updateClazz(cls) == 1)
            return Result.ok();
        return Result.error("修改失败");
    }

    @PutMapping("/showTeacher")
    public Result<List<User>> showTeacher(){
        List<User> list = new ArrayList<>();
        return Result.ok(list);
    }

    //根据姓名模糊查询教师列表（identity=2）
    @GetMapping("/teacher/byName")
    public Result<List<Map<String,String>>> getTeacherByName(@RequestParam String name) {
        List<Map<String,String>> list = clazzMapper.selectTeacherByName(name);
        return Result.ok(list);
    }

    @GetMapping("/showClazz")
    public Result<PageRespVO<ClazzVO>> showClazz(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10")  Integer size,
            @RequestParam(required = false)    String keyword) {
        return Result.ok(clazzService.getClazzVOPage(page, size, keyword));
    }

    @DeleteMapping("/deleteClazz/{classId}")
    public Result<Void> deleteClazz(@PathVariable Long classId){
        if(clazzMapper.deleteClazz(classId) == 1)
            return Result.ok();
        return Result.error("删除班级失败，请稍后再试！");
    }

    //教师端查看班级
    @GetMapping("/viewClass")
    public Result<PageRespVO<ClazzVO>> viewClass(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            HttpServletRequest req) {

        return Result.ok(clazzService.viewClass(page, size, keyword,req));
    }

    //学生端查看班级
    @GetMapping("/student/viewClass")
    public Result<PageRespVO<ClazzVO>> studentViewClass(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            HttpServletRequest req) {

        return Result.ok(clazzService.studentViewClass(page, size, keyword,req));
    }

    @PostMapping("/student/joinClass")
    public Result<Void> joinClass(@RequestBody Clazz clazz, HttpServletRequest req) {

        return clazzService.joinClass(clazz.getClassCode(),req);
    }

    @GetMapping("manageClass/{classId}")
    public Result<List<UserVO>> manageClass(@PathVariable Long classId) {
        return clazzService.manageClass(classId);
    }

    @PostMapping("/approve/{action}")
    public Result<Void> approveClass(@PathVariable int action, @RequestBody ApproveDTO dto, HttpServletRequest req) {
        return clazzService.approveClass(action,dto.getClassId(),dto.getStudentId());
    }
}
