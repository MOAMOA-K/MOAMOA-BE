package com.example.BE.coupon.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupons")
@Getter
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "name", length = 64)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "point_cost", nullable = false)
    private Long pointCost;

    @Column(name = "valid_until")
    private LocalDate validUntil;

    @Builder
    public CouponEntity(
            Long storeId,
            String name,
            String description,
            Long pointCost,
            LocalDate validUntil) {
        this.storeId = storeId;
        this.name = name;
        this.description = description;
        this.pointCost = pointCost;
        this.validUntil = validUntil;
    }
}
