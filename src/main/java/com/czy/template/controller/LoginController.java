package com.czy.template.controller;

import com.czy.template.mapper.UserMapper;
import com.czy.template.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LoginController {

    @Autowired
    UserMapper userMapper;

    // 访问根路径时返回 index.html，可以使用户直接访问登录页面
    @RequestMapping("/")
    public String index() {
        return "html/index";
    }

    //登录请求
    @PostMapping("/dologin")
    public String loginIn(@RequestParam String username,
                          @RequestParam String password,
                          HttpServletRequest req){
        User user = userMapper.findByUsername(username);
        if(user != null && user.getPassword().equals(password)){
            req.getSession().setAttribute("user",user);
            req.getSession().setAttribute("username",user.getUsername());
            return "redirect:/homepage";
        }

        return "html/index";
    }

    //重定向到首页，为了防止别人看到请求头
    @RequestMapping("/homepage")
    public String homepage(HttpServletRequest req,
                           Model model) {
        model.addAttribute("username",req.getSession().getAttribute("username"));
        return "html/homepage";
    }

    //访问注册页面
    @RequestMapping("/register")
    public String register() {
        return "html/register";
    }


    //注册页面提交表单时发送的请求
    @PostMapping("/doregister")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           @RequestParam String phone,
                           @RequestParam String email,
                           RedirectAttributes reda){
        if(username.length() > 12){
            reda.addFlashAttribute("errorMessage","用户名超过最大长度！");
            return "redirect:/register";
        }

        if(!password.equals(confirmPassword)){
            reda.addFlashAttribute("errorMessage","两次密码不相同！");
            return "redirect:/register";
        }

        User user = userMapper.findByUsername(username);
        if(user == null){
            User user1 = new User(0,"未填写",username, password, phone, email,'空',"未填写");
            int i = userMapper.registerUser(user1);
            if(i == 1) {
                return "redirect:/";
            } else {
                reda.addFlashAttribute("errorMessage","注册失败！");
                return "redirect:/register";
            }
        } else {
            reda.addFlashAttribute("errorMessage","用户名重复！");
            return "redirect:/register";
        }
    }

    //个人信息页面请求
    @RequestMapping("/html/information")
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
        return "html/information";
    }

    //修改个人信息页面请求
    @RequestMapping("/html/modifyInformation")
    public String modifyInformation(HttpServletRequest req,
                                    Model model) {
        User user = (User)req.getSession().getAttribute("user");
        model.addAttribute("realname",user.getRealname());
        model.addAttribute("username",user.getUsername());
        model.addAttribute("phone",user.getPhone());
        model.addAttribute("email",user.getEmail());
        model.addAttribute("address",user.getAddress());
        return "html/modifyInformation";
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
        User user = new User(((User)req.getSession().getAttribute("user")).getId(),realname,(String)req.getSession().getAttribute("username"), ((User)req.getSession().getAttribute("user")).getPassword(), phone, email,gender,address);
        if(userMapper.modifyPersonalInformation(user) == 1) {
            System.out.println(123);
            req.getSession().setAttribute("user",user);
            req.getSession().setAttribute("username",user.getUsername());
            return "html/modifyInformation";
        } else {
            System.out.println(456);
            return "redirect:/html/modifyInformation";
        }
    }

    //修改密码页面的请求
    @RequestMapping("/html/changePassword")
    public String changePassword(HttpServletRequest req,
                              Model model) {
        model.addAttribute("username",req.getSession().getAttribute("username"));
        return "html/changePassword";
    }
}
