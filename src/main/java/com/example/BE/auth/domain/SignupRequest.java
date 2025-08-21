package com.example.BE.auth.domain;

import com.example.BE.user.domain.UserEntity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignupRequest (
    @NotBlank(message = "닉네임은 공백일 수 없습니다.")
    @Size(min = 1, max=16, message = "닉네임은 1자~16자여야 합니다.")
    String nickname,

    @Email(message = "이메일 형식으로 입력해 주세요.")
    @Size(min=1, max=32, message="이메일은 최대 32자입니다.")
    String email,

    @Size(min=1, max=16, message="비밀번호는 최대 16자리입니다.")
    String password,

    @NotNull(message = "유저 타입은 필수 정보입니다.")
    UserRole role
){}
