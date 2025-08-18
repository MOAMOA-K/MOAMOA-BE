package com.example.BE.usercoupon.controller;

import com.example.BE.usercoupon.controller.dto.UserCouponCreateRequest;
import com.example.BE.usercoupon.service.UserCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-coupon")
public class UserCouponApiController {

    private final UserCouponService userCouponService;

    @PostMapping
    public ResponseEntity<?> create(
            @AuthenticationPrincipal Long userId,
            @RequestBody UserCouponCreateRequest request) {
        userCouponService.create(request, userId);

        return ResponseEntity.status(201)
                .body("쿠폰이 발급되었습니다.");
    }

    @DeleteMapping
    public ResponseEntity<?> delete(
            @AuthenticationPrincipal Long userId,
            @RequestBody Long userCouponId) {
        userCouponService.delete(userCouponId);

        return ResponseEntity.status(200)
                .body("쿠폰이 삭제되었습니다.");
    }

}
