package com.example.BE.coupon.service;

import com.example.BE.coupon.controller.dto.CouponCreateRequest;
import com.example.BE.coupon.controller.dto.CouponResponse;
import com.example.BE.coupon.repository.CouponRepository;
import com.example.BE.global.exception.CustomException;
import com.example.BE.global.exception.errorCode.CouponErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public void create(CouponCreateRequest request) {
        couponRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CouponResponse getCouponsByStore(Long storeId) {
        return CouponResponse.from(couponRepository.findAllByStoreId(storeId));
    }

    @Transactional
    public void delete(Long couponId) {
        if (!couponRepository.existsById(couponId)) {
            throw CustomException.from(CouponErrorCode.NOT_FOUND);
        }
        // TODO: userCoupon 먼저 지우기
        couponRepository.deleteById(couponId);
    }

}
