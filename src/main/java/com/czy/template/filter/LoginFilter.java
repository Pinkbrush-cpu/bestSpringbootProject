//package com.czy.template.filter;
//
//import com.czy.template.pojo.User;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class LoginFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        //获取当前的req和resp
//        HttpServletRequest req= (HttpServletRequest) servletRequest;
//        HttpServletResponse resp= (HttpServletResponse) servletResponse;
//
//        //从当前req中获取当前访问的接口路径
//        String path=req.getRequestURI();
//
//        //判断访问的接口是否为dologin、login...等
//        if(path.contains("/login") || path.contains("/dologin") || path.contains("/css/") || path.contains("/js/") || path.contains("/images/") || path.contains("/doregister")){
//            //直接放行
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }
//        //获取当前存在的用户信息
//        User user = (User) req.getSession().getAttribute("user");
//
//        //若存在user，则放行
//        //否则跳转到/login提示用户进行登入
//        if(user != null){
//            filterChain.doFilter(servletRequest, servletResponse);
//        } else{
//            resp.sendRedirect("/login");
//        }
//    }
//
//    @Override
//    public void destroy() {
//        Filter.super.destroy();
//    }
//}
