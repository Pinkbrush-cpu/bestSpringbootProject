package com.czy.template.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    private TokenValidationFilter tokenValidationFilter;

    @Bean
    public FilterRegistrationBean<TokenValidationFilter> tokenFilterRegistration() {
        FilterRegistrationBean<TokenValidationFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(tokenValidationFilter);
        registration.addUrlPatterns("/*"); // 过滤所有请求
        registration.setName("tokenValidationFilter");
        registration.setOrder(1); // 设置过滤器顺序
        return registration;
    }
}