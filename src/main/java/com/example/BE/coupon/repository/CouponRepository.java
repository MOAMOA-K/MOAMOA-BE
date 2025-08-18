package com.example.BE.coupon.repository;

import com.example.BE.coupon.domain.CouponEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

    public List<CouponEntity> findAllByStoreId(Long storeId);
}
