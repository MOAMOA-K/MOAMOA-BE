package com.example.BE.usercoupon.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usercoupon")
@Getter
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCouponEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @Column(name = "is_used")
    private Boolean isUsed;

    @Builder
    public UserCouponEntity(Long userId, Long couponId, Boolean isUsed) {
        this.userId = userId;
        this.couponId = couponId;
        this.isUsed = isUsed;
    }
}
