package com.example.BE.user.domain.dto;

public record UserDetailResponse(
        Long id,
        String nickname,
        String email,
        Long points,
        Long feedbackCount,
        Long couponCount
) {

}
