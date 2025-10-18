package com.czy.template.controller;


import com.czy.template.dto.PageRespDTO;
import com.czy.template.pojo.Clazz;
import com.czy.template.pojo.User;
import com.czy.template.util.Result;
import com.czy.template.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pink/class")
public class ClassController {

    public Result<Void> addClass(@RequestBody Clazz cls){



        return Result.ok();
    }

    public Result<List<User>> showTeacher(){
        List list = new ArrayList<User>();
        return Result.ok(list);
    }

    public Result<PageRespDTO<Clazz>> showClazz(
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "8")  Integer size,
                            @RequestParam(required = false)    String keyword,
                            HttpServletRequest request) {

        /* ① 当前登录用户 */
        long currentId = jwtUtil.getUserFromRequest(request).getId();
        boolean hideOne = currentId != 1;

        /* ② 分页查询（已过滤 id=1） */
        long offset = (long) (page - 1) * size;
        long total  = userMapper.countByKeyword(keyword, hideOne);
        List<UserVO> records = userMapper.selectPageByKeyword(offset, size, keyword, hideOne);

        /* ③ 身份排序（管理员>教师>学生） */
        List<Integer> order = List.of(10, 2, 1);
        records.sort(Comparator
                .comparingInt((UserVO u) -> order.indexOf(u.getIdentity()))
                .thenComparing(UserVO::getId));

        long pages = (total + size - 1) / size;
        return Result.ok(new PageRespDTO<>(records, total, page, size, pages));
    }
}
