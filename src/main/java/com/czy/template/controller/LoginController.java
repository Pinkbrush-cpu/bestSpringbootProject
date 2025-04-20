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

    // 访问根路径时返回 index.html
    @RequestMapping("/")
    public String index() {
        return "html/index";
    }

    @PostMapping("/dologin")
    public String loginIn(@RequestParam String username,
                          @RequestParam String password,
                          HttpServletRequest req){
        User user = userMapper.findByUsername(username);
        if(user != null && user.getPassword().equals(password)){
            req.getSession().setAttribute("user",user);
            return "redirect:/homepage";
        }

        return "html/index";
    }

    @RequestMapping("/homepage")
    public String homepage(HttpServletRequest req,
                           Model model) {
        User user = (User)req.getSession().getAttribute("user");
        model.addAttribute("username",user.getUsername());
        return "html/homepage";
    }

    @RequestMapping("/register")
    public String register() {
        return "html/register";
    }


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
            User user1 = new User(0,username, password, phone, email);
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

    @RequestMapping("/html/information")
    public String information(HttpServletRequest req,
                              Model model) {
        User user = (User)req.getSession().getAttribute("user");
        model.addAttribute("username",user.getUsername());
        return "html/information";
    }

}
