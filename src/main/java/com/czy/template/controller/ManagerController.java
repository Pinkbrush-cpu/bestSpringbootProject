package com.czy.template.controller;

import com.czy.template.mapper.UserMapper;
import com.czy.template.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ManagerController {

    @Autowired
    UserMapper userMapper;

    //重定向到首页，为了防止别人看到请求头
    @RequestMapping("/managerHomepage")
    public String managerHomepage(HttpServletRequest req,
                                  Model model) {
        model.addAttribute("username", req.getSession().getAttribute("username"));
        return "html/manager/managerHomepage";
    }

    @RequestMapping("/managerChangePassword")
    public String managerChangePassword(HttpServletRequest req,
                                        Model model) {
        model.addAttribute("username", req.getSession().getAttribute("username"));
        return "html/manager/managerChangePassword";
    }


    //返回管理员修改权限界面并且调用用户返回列表
    @RequestMapping("/managerModifyPermission")
    public String modifyPermission(){
        return "redirect:/managerAllUser";
    }

    @RequestMapping("/managerAllUser")
    public String managerAllUser(HttpServletRequest req,
                                 Model model){
        List<User> users = userMapper.selectAllUser();
        model.addAttribute("users",users);
        model.addAttribute("username",req.getSession().getAttribute("username"));
        model.addAttribute("currentUserId", req.getSession().getAttribute("userId"));
        return "html/manager/managerAllUser";
    }

    //修改学生权限为老师
    @RequestMapping("/setTeacher")
    public String setTeacher(String username){
        User user = userMapper.findByUsername(username);
        user.setIdentity(2);
        userMapper.setAndCancel(user);
        return "redirect:/managerAllUser";

    }

    //修改老师权限为学生
    @RequestMapping("/cancelTeacher")
    public String cancelTeacher(String username){
        User user = userMapper.findByUsername(username);
        user.setIdentity(1);
        userMapper.setAndCancel(user);
        return "redirect:/managerAllUser";
    }

    //修改学生权限为管理员
    @RequestMapping("/setManager")
    public String setManager(String username){
        User user = userMapper.findByUsername(username);
        user.setIdentity(10);
        userMapper.setAndCancel(user);
        return "redirect:/managerAllUser";
    }

    //修改管理员权限为学生
    @RequestMapping("/cancelManager")
    public String cancelManager(String username){
        User user = userMapper.findByUsername(username);
        user.setIdentity(1);
        userMapper.setAndCancel(user);
        return "redirect:/managerAllUser";
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

    @RequestMapping("/searchUsers")
    public String searchUsers(String search,
                              HttpServletRequest req,
                              Model model){
        List<User> users = userMapper.searchUsers(search);
        model.addAttribute("users",users);
        model.addAttribute("username",req.getSession().getAttribute("username"));
        model.addAttribute("currentUserId", req.getSession().getAttribute("userId"));
        return "html/manager/managerAllUser";
    }
}
