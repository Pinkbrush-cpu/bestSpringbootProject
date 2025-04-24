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
public class UserController {

    @Autowired
    UserMapper userMapper;

    //重定向到首页，为了防止别人看到请求头
    @RequestMapping("/homepage")
    public String homepage(HttpServletRequest req,
                           Model model) {
        model.addAttribute("username",req.getSession().getAttribute("username"));
        return "html/user/userHomepage";
    }

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
        return "html/user/userInformation";
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
        return "html/user/userModifyInformation";
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
        User user = new User(((User)req.getSession().getAttribute("user")).getId(),realname,(String)req.getSession().getAttribute("username"), ((User)req.getSession().getAttribute("user")).getPassword(), phone, email,gender,address,1);
        if(userMapper.modifyPersonalInformation(user) == 1) {
            req.getSession().setAttribute("user",user);
            req.getSession().setAttribute("userId",user.getId());
            req.getSession().setAttribute("username",user.getUsername());
            return "html/user/userModifyInformation";
        } else {
            return "redirect:/modifyInformation";
        }
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
