package com.example.BE.coupon.domain;

import com.example.BE.global.base.BaseTimeEntity;
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
public class CouponEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "store_name", length = 64, nullable = false)
    private String storeName;

    @Column(name = "name", length = 64)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "point_cost", nullable = false)
    private Long pointCost;

    @Column(name = "valid_until")
    private LocalDate validUntil;

    //TODO: 해커톤이니 비번 암호화 안할래
    @Column(name = "password", nullable = false)
    private String password;

    @Builder
    public CouponEntity(
            Long storeId,
            String name,
            String storeName,
            String description,
            Long pointCost,
            LocalDate validUntil,
            String password
    ) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.name = name;
        this.description = description;
        this.pointCost = pointCost;
        this.validUntil = validUntil;
        this.password = password;
    }

    public boolean isExpired() {
        return validUntil.isBefore(LocalDate.now());
    }
}
