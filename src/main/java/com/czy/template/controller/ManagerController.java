package com.czy.template.controller;

import com.czy.template.dto.ModifyInformationDTO;
import com.czy.template.dto.PageRespDTO;
import com.czy.template.mapper.UserMapper;
import com.czy.template.pojo.User;
import com.czy.template.util.JwtUtil;
import com.czy.template.util.Result;
import com.czy.template.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pink/admin")
public class ManagerController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtUtil jwtUtil;

    //分页返回用户
    @GetMapping("/managerAllUser")
    public Result<PageRespDTO<UserVO>> managerAllUser(
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

    @GetMapping("/{userId}/profile")
    public Result<ModifyInformationDTO> getUserProfile(@PathVariable Long userId) {
        return Result.ok(
                userMapper.selectUserById(userId)
                        .orElseThrow(() -> new RuntimeException("用户不存在"))
        );
    }

    //修改学生权限为老师
    @RequestMapping("/setTeacher")
    public Result<Void> setTeacher(@RequestBody Map<String,String> map){
        String username = map.get("username");
        User user = userMapper.findByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        }
        user.setIdentity(2);
        userMapper.setAndCancel(user);
        return Result.ok();

    }

    //修改老师权限为学生
    @RequestMapping("/cancelTeacher")
    public Result<Void> cancelTeacher(@RequestBody Map<String,String> map){
        String username = map.get("username");
        User user = userMapper.findByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        }
        user.setIdentity(1);
        userMapper.setAndCancel(user);
        return Result.ok();
    }

    //修改学生权限为管理员
    @RequestMapping("/setManager")
    public Result<Void> setManager(@RequestBody Map<String,String> map){
        String username = map.get("username");
        User user = userMapper.findByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        }
        user.setIdentity(10);
        userMapper.setAndCancel(user);
        return Result.ok();
    }

    //修改管理员权限为学生
    @RequestMapping("/cancelManager")
    public Result<Void> cancelManager(@RequestBody Map<String,String> map){
        String username = map.get("username");
        User user = userMapper.findByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        }
        user.setIdentity(1);
        userMapper.setAndCancel(user);
        return Result.ok();
    }

    //统计用户数量
    @RequestMapping("/managerUserStatistics")
    public String managerUserStatistics(HttpServletRequest req,
                                        Model model){
        List<User> users = userMapper.selectAllUser();
        int managerCount = 0;
        int teacherCount = 0;
        int userCount = 0;
        for (User user : users) {
            if(user.getIdentity() == 10){
                managerCount++;
            } else if (user.getIdentity() == 2) {
                teacherCount++;
            } else if (user.getIdentity() == 1) {
                userCount++;
            }
        }
        model.addAttribute("username",req.getSession().getAttribute("username"));
        model.addAttribute("users",users);
        model.addAttribute("currentUserId", req.getSession().getAttribute("userId"));
        model.addAttribute("managerCount", managerCount);
        model.addAttribute("teacherCount", teacherCount);
        model.addAttribute("userCount", userCount);
        return "html/manager/managerUserStatistics";
    }

}
