package com.example.BE.usercoupon.repository;

import com.example.BE.usercoupon.domain.UserCouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCouponRepository extends JpaRepository<UserCouponEntity, Long> {

    void deleteAllByCouponId(Long couponId);


}
