package com.example.BE.receipt.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "receipts")
@Getter
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiptEntity {

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
    private ReceiptStatus status;

    @Column(name = "total_price")
    private Long totalPrice;

    @Builder
    public ReceiptEntity(Long userId, Long storeId, String storeName,
            LocalDateTime issuedAt, ReceiptStatus status, Long totalPrice) {
        this.userId = userId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.issuedAt = issuedAt;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public enum ReceiptStatus{
        AVAILABLE,
        DONE
    }
}
