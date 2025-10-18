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


    /* -------------------- 验证令牌有效性 -------------------- */
    public boolean validateToken(String token) {
        try {
            // 尝试解析令牌，如果解析成功且未过期，则令牌有效
            Claims claims = parseToken(token);

            // 检查是否过期（parseToken内部已经会检查过期，这里额外确保）
            return claims.getExpiration().after(new Date());
        } catch (ExpiredJwtException e) {
            // 令牌过期
            System.out.println("JWT令牌已过期: " + e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            // 令牌格式错误
            System.out.println("JWT令牌格式错误: " + e.getMessage());
            return false;
        } catch (SignatureException e) {
            // 签名验证失败
            System.out.println("JWT签名无效: " + e.getMessage());
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            // 其他JWT异常
            System.out.println("JWT令牌无效: " + e.getMessage());
            return false;
        }
    }
}