package com.czy.template.filter;

import com.czy.template.util.JwtUtil;
import com.czy.template.util.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class TokenValidationFilter implements Filter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    // 不需要token验证的路径
    private static final List<String> EXCLUDE_PATHS = Arrays.asList(
            "/dologin",
            "doregister"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // 检查是否在排除路径中
        if (isExcludePath(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        // 获取token
        String token = jwtUtil.resolveToken(httpRequest);

        // 如果没有token，直接放行（因为你说不需要强制认证）
        if (!StringUtils.hasText(token)) {
            chain.doFilter(request, response);
            return;
        }

        // 有token时验证token是否过期
        try {
            if (jwtUtil.validateToken(token)) {
                // token有效，继续执行
                chain.doFilter(request, response);
            } else {
                // token无效（非过期原因）
                chain.doFilter(request, response);
            }
        } catch (ExpiredJwtException e) {
            // token过期，返回401错误
            handleTokenExpired(httpResponse, e);
        } catch (Exception e) {
            // 其他token错误，直接放行
            chain.doFilter(request, response);
        }
    }

    /**
     * 检查请求路径是否在排除列表中
     */
    private boolean isExcludePath(String requestURI) {
        return EXCLUDE_PATHS.stream().anyMatch(path ->
                requestURI.startsWith(path) ||
                        requestURI.equals(path) ||
                        requestURI.equals(path + "/")
        );
    }

    /**
     * 处理token过期情况
     */
    private void handleTokenExpired(HttpServletResponse response, ExpiredJwtException e) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");

        Result<Object> result = Result.error(401, "Token已过期，请重新登录");

        String jsonResponse = objectMapper.writeValueAsString(result);
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }

    @Override
    public void destroy() {
        // 销毁方法，可选
    }
}