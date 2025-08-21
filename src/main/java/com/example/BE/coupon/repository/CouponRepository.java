package com.example.BE.coupon.repository;

import com.example.BE.coupon.domain.CouponEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

    public List<CouponEntity> findAllByStoreId(Long storeId);


    @Query("SELECT c FROM CouponEntity c WHERE c.validUntil >= :currentDate")
    public List<CouponEntity> findCoupons(@Param("currentDate") LocalDate currentDate);
}
