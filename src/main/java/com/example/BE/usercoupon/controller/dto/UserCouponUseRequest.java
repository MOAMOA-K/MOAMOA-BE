package com.example.BE.usercoupon.controller.dto;

public record UserCouponUseRequest(
        Long userCouponId,
        String password
) {

}
