package com.czy.template.controller;

import com.czy.template.mapper.UserMapper;
import com.czy.template.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublicController {

    @Autowired
    UserMapper userMapper;


    //个人信息页面请求
    @RequestMapping("/information")
    public String information(HttpServletRequest req,
                                     Model model) {
        User user = (User)req.getSession().getAttribute("user");
        model.addAttribute("realname",user.getRealname());
        model.addAttribute("username",user.getUsername());
        model.addAttribute("phone",user.getPhone());
        model.addAttribute("email",user.getEmail());
        if(user.getGender() == '空') {
            model.addAttribute("gender", "未填写");
        } else {
            model.addAttribute("gender", user.getGender());
        }
        model.addAttribute("address",user.getAddress());
        if(user.getIdentity() == 10) {
            return "html/manager/managerInformation";
        } else if (user.getIdentity() == 2) {
            return "html/teacher/teacherInformation";
        } else {
            return "html/user/userInformation";
        }
    }

    //修改个人信息页面请求
    @RequestMapping("/modifyInformation")
    public String modifyInformation(HttpServletRequest req,
                                    Model model) {
        User user = (User)req.getSession().getAttribute("user");
        model.addAttribute("realname",user.getRealname());
        model.addAttribute("username",user.getUsername());
        model.addAttribute("phone",user.getPhone());
        model.addAttribute("email",user.getEmail());
        model.addAttribute("address",user.getAddress());
        if(user.getIdentity() == 10) {
            return "html/manager/managerModifyInformation";
        } else if (user.getIdentity() == 2) {
            return "html/teacher/teacherModifyInformation";
        } else {
            return "html/user/userModifyInformation";
        }
    }

    //修改个人信息页面表单的请求头
    @RequestMapping("/doModifyPersonalInformation")
    public String modifyPersonalInformation(@RequestParam String realname,
                                            @RequestParam String phone,
                                            @RequestParam String email,
                                            @RequestParam char gender,
                                            @RequestParam String address,
                                            HttpServletRequest req) {
        if(gender == 'm'){
            gender = '男';
        } else {
            gender = '女';
        }
        User user = (User) req.getSession().getAttribute("user");
        User newUser = new User(user.getId(),realname,user.getUsername(), user.getPassword(), phone, email,gender,address,user.getIdentity());
        userMapper.modifyPersonalInformation(newUser);
        req.getSession().setAttribute("user",newUser);
        req.getSession().setAttribute("userId",newUser.getId());
        req.getSession().setAttribute("username",newUser.getUsername());
        return "redirect:/modifyInformation";

    }

    //修改密码页面的请求
    @RequestMapping("/changePassword")
    public String changePassword(HttpServletRequest req,
                                 Model model) {
        model.addAttribute("username",req.getSession().getAttribute("username"));
        return "html/user/userChangePassword";
    }

    @RequestMapping("/doChangePassword")
    public String doChangePassword(@RequestParam String password,
                                   @RequestParam String newPassword,
                                   @RequestParam String confirmPassword,
                                   HttpServletRequest req){
        User user = (User)req.getSession().getAttribute("user");
        if(user != null && user.getPassword().equals(password) && newPassword.equals(confirmPassword)){
            if(userMapper.modifyPassword(newPassword,user.getId()) == 1){
                return "redirect:/changePassword";
            }
        }
        return "html/user/userChangePassword";
    }
}
