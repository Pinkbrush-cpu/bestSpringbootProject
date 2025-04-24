package com.czy.template.controller;

import com.czy.template.mapper.UserMapper;
import com.czy.template.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/managerInformation")
    public String managerInformation(HttpServletRequest req,
                                     Model model) {
        model.addAttribute("username", req.getSession().getAttribute("username"));
        return "html/manager/managerInformation";
    }

    @RequestMapping("/managerModifyInformation")
    public String managerModifyInformation(HttpServletRequest req,
                                           Model model) {
        model.addAttribute("username", req.getSession().getAttribute("username"));
        return "html/manager/managerModifyInformation";
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
        model.addAttribute("currentUserId", req.getSession().getAttribute("userId"));
        return "html/manager/managerAllUser";
    }
}
