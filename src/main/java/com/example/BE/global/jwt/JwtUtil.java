package com.example.BE.global.jwt;

import com.example.BE.user.domain.UserEntity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtil {
    private final SecretKey secretKey;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // claim 추출
    private Claims getClaims(String token){
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public Long getId(String token){
        return getClaims(token).get("id", Long.class);
    }

    public String getEmail(String token) {
        return getClaims(token).get("email", String.class);
    }

    public String getRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token){
        try{
            getClaims(token);
            return true;
        }catch(JwtException e){
            log.warn("Invalid Token(validateToken method): {}", e.getMessage());
            return false;
        }
    }

    // 토큰 생성
    public String generateToken(Long userId, String email, UserRole role) {
        long expireTimeMs = 1000 * 60 * 60; // 유효기간 1시간
        Date now = new Date();

        return Jwts.builder()
            .claim("id", userId)
            .claim("email", email)
            .claim("role", role.name())
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + expireTimeMs))
            .signWith(secretKey)
            .compact();
    }

}
