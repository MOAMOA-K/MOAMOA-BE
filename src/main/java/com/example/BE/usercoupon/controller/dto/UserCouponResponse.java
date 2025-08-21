package com.example.BE.usercoupon.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserCouponResponse(
        Long userCouponId,
        String storeName,
        String couponName,
        LocalDate validUntil,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime createdAt
) {

}
