package com.example.BE.coupon.controller;

import com.example.BE.coupon.controller.dto.CouponCreateRequest;
import com.example.BE.coupon.controller.dto.CouponResponse;
import com.example.BE.coupon.service.CouponService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponApiController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<?> create(
            @AuthenticationPrincipal Long userId,
            @RequestBody CouponCreateRequest request
    ) {
        couponService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("쿠폰이 생성되었습니다.");
    }
    
    @GetMapping
    public ResponseEntity<?> getCoupons(
    ) {
        List<CouponResponse> response = couponService.getCoupons();
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    //TODO: 사장님
    @GetMapping("/store/{storeId}")
    public ResponseEntity<?> getCouponsByStore(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long storeId
    ) {
        couponService.getCouponsByStore(storeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(couponService.getCouponsByStore(storeId));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(
            @AuthenticationPrincipal Long userId,
            @RequestBody Long couponId
    ) {
        couponService.delete(couponId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("쿠폰이 삭제되었습니다.");
    }

}
