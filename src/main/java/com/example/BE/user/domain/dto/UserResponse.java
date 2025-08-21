package com.example.BE.user.domain.dto;

import com.example.BE.user.domain.UserEntity;
import com.example.BE.user.domain.UserEntity.UserRole;
import lombok.Builder;

@Builder
public record UserResponse(
    Long id,
    String email,
    String nickname,
    UserRole role,
    Long points
) {
    public static UserResponse from(UserEntity user) {
        return UserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .role(user.getRole())
            .points(user.getPoints())
            .build();
    }
}