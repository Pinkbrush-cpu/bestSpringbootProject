package com.czy.template.util;

import com.czy.template.pojo.User;
import com.czy.template.mapper.UserMapper;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 24h

    @Autowired
    private UserMapper userMapper;   // 用于查库得到完整 User

    /* -------------------- 生成令牌 -------------------- */
    public String generateToken(String username, int identity) {
        return Jwts.builder()
                .setSubject(username)
                .claim("identity", identity)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /* -------------------- 解析令牌 -------------------- */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /* -------------------- 从请求头拿 token -------------------- */
    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    /* -------------------- 从请求头直接拿用户名 -------------------- */
    public String getUsernameFromRequest(HttpServletRequest request) {
        String token = resolveToken(request);
        return token == null ? null : parseToken(token).getSubject();
    }

    /* -------------------- 从请求头直接拿完整 User -------------------- */
    public User getUserFromRequest(HttpServletRequest request) {
        String username = getUsernameFromRequest(request);
        return username == null ? null : userMapper.findByUsername(username);
    }


}