package com.example.BE.coupon.service;

import com.example.BE.coupon.controller.dto.CouponCreateRequest;
import com.example.BE.coupon.controller.dto.CouponResponse;
import com.example.BE.coupon.repository.CouponRepository;
import com.example.BE.global.exception.CustomException;
import com.example.BE.global.exception.errorCode.CouponErrorCode;
import com.example.BE.global.exception.errorCode.StoreErrorCode;
import com.example.BE.store.domain.StoreEntity;
import com.example.BE.store.repository.StoreRepository;
import com.example.BE.usercoupon.repository.UserCouponRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void create(CouponCreateRequest request) {
        StoreEntity store = storeRepository.findById(request.storeId())
                .orElseThrow(() -> CustomException.from(StoreErrorCode.STORE_NOT_FOUND));

        couponRepository.save(request.toEntity(store.getName()));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CouponResponse> getCoupons() {
        return CouponResponse.from(couponRepository.findCoupons(LocalDate.now()));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CouponResponse> getCouponsByStore(Long storeId) {
        return CouponResponse.from(couponRepository.findAllByStoreId(storeId));
    }

    @Transactional
    public void delete(Long couponId) {
        //TODO: 사장님 & 관리자 권한 체크
        if (!couponRepository.existsById(couponId)) {
            throw CustomException.from(CouponErrorCode.NOT_FOUND);
        }

        userCouponRepository.deleteAllByCouponId(couponId);
        couponRepository.deleteById(couponId);
    }

}
