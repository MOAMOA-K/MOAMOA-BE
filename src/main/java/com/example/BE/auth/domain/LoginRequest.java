package com.example.BE.auth.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record LoginRequest (
    @Email(message = "이메일 형식으로 입력해 주세요.")
    @Size(min=1, max=16, message="이메일은 최대 32자입니다.")
    String email,

    @Size(min=1, max=16, message="비밀번호는 최대 16자리입니다.")
    String password
){}
