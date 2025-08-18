package com.example.BE.coupon.controller.dto;

import com.example.BE.coupon.domain.CouponEntity;
import java.util.List;

public record CouponResponse(
        Long id,
        String name,
        String description,
        Long pointCost,
        String validUntil
) {

    public static List<CouponResponse> from(List<CouponEntity> entity) {
        return entity.stream()
                .map(coupon -> new CouponResponse(
                        coupon.getId(),
                        coupon.getName(),
                        coupon.getDescription(),
                        coupon.getPointCost(),
                        coupon.getValidUntil() != null ? coupon.getValidUntil().toString() : null
                ))
                .toList();
    }
}
