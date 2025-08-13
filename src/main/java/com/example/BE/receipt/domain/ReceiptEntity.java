package com.example.BE.receipt.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Receipt")
@Getter
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiptEntity{

    public enum Status {
        pending, available, expired
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "store_name", length = 128)
    private String storeName;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private Status status;

    @Column(name = "total_price")
    private Long totalPrice;

    @Builder
    public ReceiptEntity(Long userId, Long storeId, String storeName,
            LocalDateTime issuedAt, Status status, Long totalPrice) {
        this.userId = userId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.issuedAt = issuedAt;
        this.status = status;
        this.totalPrice = totalPrice;
    }
}
