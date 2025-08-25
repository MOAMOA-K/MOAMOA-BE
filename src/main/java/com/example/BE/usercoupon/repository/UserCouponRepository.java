package com.example.BE.usercoupon.repository;

import com.example.BE.usercoupon.controller.dto.UserCouponResponse;
import com.example.BE.usercoupon.domain.UserCouponEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserCouponRepository extends JpaRepository<UserCouponEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM UserCouponEntity uc WHERE uc.couponId = :couponId")
    void deleteAllByCouponId(@Param("couponId") Long couponId);

    @Query(
            """
                    SELECT new com.example.BE.usercoupon.controller.dto.UserCouponResponse(
                        uc.id,
                        c.storeName,
                        c.description,
                        c.name,
                        c.validUntil,
                        uc.createdAt
                    )
                    FROM UserCouponEntity uc
                    JOIN CouponEntity c ON uc.couponId = c.id
                    WHERE uc.userId = :userId
                      AND uc.isUsed = false
                      AND c.validUntil >= CURRENT_DATE
                    ORDER BY uc.createdAt DESC
                    """

    )
    List<UserCouponResponse> getUserCoupons(@Param("userId") Long userId);
}
