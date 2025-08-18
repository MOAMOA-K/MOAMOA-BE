package com.example.BE.coupon.controller;

import com.example.BE.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
public class CouponApiController {

    private final CouponService couponService;

    @PostMapping
    public void create() {

    }

}
