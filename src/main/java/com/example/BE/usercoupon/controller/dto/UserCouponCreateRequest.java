package com.example.BE.usercoupon.controller.dto;

import com.example.BE.usercoupon.domain.UserCouponEntity;

public record UserCouponCreateRequest(
        Long couponId
) {

    public UserCouponEntity toEntity(Long userId) {
        return UserCouponEntity.builder()
                .userId(userId)
                .couponId(couponId)
                .isUsed(false)
                .build();

    }
}
