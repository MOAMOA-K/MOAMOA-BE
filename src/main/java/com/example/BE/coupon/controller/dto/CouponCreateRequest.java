package com.example.BE.coupon.controller.dto;

import com.example.BE.coupon.domain.CouponEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CouponCreateRequest(
        Long storeId,
        @Max(value = 100, message = "쿠폰 이름은 100자 이하여야 합니다.")
        String name,

        @Max(value = 500, message = "쿠폰 설명은 500자 이하여야 합니다.")
        String description,

        @NotBlank
        String password,

        @NotNull
        @Min(value = 0, message = "최소 할인 금액은 0원 이상이어야 합니다.")
        Long pointCost,

        LocalDate validUntil
) {

    public CouponEntity toEntity(String storeName) {
        return CouponEntity.builder()
                .storeId(storeId)
                .storeName(storeName)
                .name(name)
                .description(description)
                .password(password)
                .pointCost(pointCost)
                .validUntil(validUntil)
                .build();
    }
}
