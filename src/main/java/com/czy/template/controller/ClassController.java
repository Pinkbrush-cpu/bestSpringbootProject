package com.czy.template.controller;


import com.czy.template.mapper.ClazzMapper;
import com.czy.template.view.dto.PageRespDTO;
import com.czy.template.pojo.Clazz;
import com.czy.template.pojo.User;
import com.czy.template.service.ClazzService;
import com.czy.template.util.Result;
import com.czy.template.view.vo.ClazzVO;
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

    @PostMapping("/addClazz")
    public Result<Void> addClazz(@RequestBody Clazz cls){

        if(clazzMapper.addClazz(cls) == 1)
            return Result.ok();
        return Result.error("新增失败");
    }

    @PutMapping("/updateClazz/{classId}")
    public Result<Void> updateClazz(@RequestBody Clazz cls){

        System.out.println(2345);
        if(clazzMapper.updateClazz(cls) == 1)
            return Result.ok();
        return Result.error("修改失败");
    }

    @PutMapping("/showTeacher")
    public Result<List<User>> showTeacher(){
        List list = new ArrayList<User>();
        return Result.ok(list);
    }

    @GetMapping("/showClazz")
    public Result<PageRespDTO<ClazzVO>> showClazz(
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10")  Integer size,
                            @RequestParam(required = false)    String keyword) {
        System.out.println(size);
        return Result.ok(clazzService.getClazzVOPage(page, size, keyword));
    }

    //根据姓名模糊查询教师列表（identity=2）
    @GetMapping("/teacher/byName")
    public Result<List<Map<String,String>>> getTeacherByName(@RequestParam String name) {
        List<Map<String,String>> list = clazzMapper.selectTeacherByName(name);
        return Result.ok(list);
    }
}
