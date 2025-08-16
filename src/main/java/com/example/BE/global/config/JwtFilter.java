package com.example.BE.global.config;

import com.example.BE.global.exception.errorCode.TokenErrorCode;
import com.example.BE.global.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        // Bearer + token string 추출
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // authorization이 비어있으면 일단 security context로 넘김 -> login/signup 같은 허용 경로는
        // 여기 조건문에 걸려서 일단 다음 filter로 넘어감.
        if (authorization == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰이 Bearer로 시작하지 않으면 바로 errorResponse 전달
        if (!authorization.startsWith("Bearer ")) {
            log.warn("Invalid Token: {}", authorization);
            setErrorResponse(response, TokenErrorCode.INVALID_TOKEN);
            return;
        }

        // Bearer 부분을 떼고 token만 추출
        String token = authorization.substring("Bearer ".length());

        // 토큰 유효성 검사: 만료 여부, 형식 다 한 번에 체크함.
        if (!jwtUtil.validateToken(token)) {
            setErrorResponse(response, TokenErrorCode.INVALID_TOKEN);
            return;
        }

        // SecurityContext에 등록
        Long userId = jwtUtil.getId(token);
        String userRole = jwtUtil.getRole(token);

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userId, null, List.of(
                new SimpleGrantedAuthority(userRole)
            ));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // next filter
        filterChain.doFilter(request, response);

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
