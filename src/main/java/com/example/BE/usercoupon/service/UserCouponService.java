package com.example.BE.usercoupon.service;

import com.example.BE.coupon.domain.CouponEntity;
import com.example.BE.coupon.repository.CouponRepository;
import com.example.BE.global.exception.CustomException;
import com.example.BE.global.exception.errorCode.CouponErrorCode;
import com.example.BE.usercoupon.controller.dto.UserCouponCreateRequest;
import com.example.BE.usercoupon.domain.UserCouponEntity;
import com.example.BE.usercoupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    @Transactional
    public void create(UserCouponCreateRequest request, Long userId) {
        CouponEntity coupon = couponRepository.findById(request.couponId())
                .orElseThrow(() -> CustomException.from(CouponErrorCode.NOT_FOUND));

        if (coupon.isExpired()) {
            throw CustomException.from(CouponErrorCode.EXPIRED);
        }

        userCouponRepository.save(request.toEntity(userId));
    }


    @Transactional
    public void delete(Long userCouponId) {
        UserCouponEntity userCoupon = findById(userCouponId);
        userCouponRepository.deleteById(userCouponId);
    }

    private void markAsUsed(UserCouponEntity userCoupon) {
        if (userCoupon.getIsUsed()) {
            throw CustomException.from(CouponErrorCode.EXPIRED);
        }

        userCoupon.markAsUsed();
        userCouponRepository.save(userCoupon);
    }

    private UserCouponEntity findById(Long userCouponId) {
        return userCouponRepository.findById(userCouponId)
                .orElseThrow(() -> CustomException.from(CouponErrorCode.NOT_FOUND));
    }
}
