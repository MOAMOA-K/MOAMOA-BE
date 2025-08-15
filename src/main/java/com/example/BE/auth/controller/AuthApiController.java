package com.example.BE.auth.controller;

import com.example.BE.auth.domain.LoginRequest;
import com.example.BE.auth.domain.SignupRequest;
import com.example.BE.user.domain.dto.MessageResponse;
import com.example.BE.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
        @Valid @RequestBody SignupRequest signupRequest
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.signUpUser(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
        @Valid @RequestBody LoginRequest loginRequest
    ){
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.login(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
        @AuthenticationPrincipal String email
    ){
        userService.logout(email);
        return ResponseEntity.status(HttpStatus.OK)
            .body(MessageResponse.from("로그아웃되었습니다."));
    }

}
