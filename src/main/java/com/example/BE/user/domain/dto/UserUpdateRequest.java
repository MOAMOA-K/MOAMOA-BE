package com.example.BE.user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
    @NotBlank(message = "닉네임은 공백일 수 없습니다.")
    @Size(min = 1, max=16, message = "닉네임은 1자~16자여야 합니다.")
    String nickname
) {}