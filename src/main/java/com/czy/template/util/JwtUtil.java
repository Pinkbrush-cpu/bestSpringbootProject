package com.czy.template.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")   // ① 非 static ② 被 Spring 管理
    private String secret;

    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 24h

    public String generateToken(String username, int identity) {
        return Jwts.builder()
                .setSubject(username)
                .claim("identity", identity)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, secret)   // 不会再为 null
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}