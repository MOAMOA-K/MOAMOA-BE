package com.example.BE.coupon.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupon")
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

    @Column(name = "valid_until")
    private LocalDateTime validUntil;

    @Builder
    public CouponEntity(Long storeId, String name, String description, LocalDateTime validUntil) {
        this.storeId = storeId;
        this.name = name;
        this.description = description;
        this.validUntil = validUntil;
    }
}
