package com.example.BE.global.config;

import com.example.BE.global.exception.errorCode.TokenErrorCode;
import com.example.BE.global.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        // Bearer + token string 추출
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // authorization이 비어있거나 Bearer로 시작하지 않으면 다음 필터로 일단 넘김
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.warn("Invalid Token: {}", authorization);
            setErrorResponse(response, TokenErrorCode.INVALID_TOKEN);
            return;
        }

        try {
            // Bearer 부분을 떼고 token만 추출
            String token = authorization.split(" ")[1];

            // 토큰 유효성 검사: 만료 여부, 형식 다 한 번에 체크함.
            if(!jwtUtil.validateToken(token)){
                throw new JwtException("Token validation failed");
            }

            // SecurityContext에 등록
            String email = jwtUtil.getEmail(token);
            String userRole = jwtUtil.getRole(token);

            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, List.of(
                    new SimpleGrantedAuthority(userRole)
                ));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // next filter
            filterChain.doFilter(request, response);

        } catch(JwtException e){
            log.warn("Invalid Token: {}", e.getMessage());
            setErrorResponse(response, TokenErrorCode.INVALID_TOKEN);
            return;
        }
    }

    private void setErrorResponse(HttpServletResponse response, TokenErrorCode errorCode)
        throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(errorCode.httpStatus().value());

        Map<String, Object> body = new HashMap<>();
        body.put("errorCode", errorCode.code());
        body.put("message", errorCode.message());

        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
