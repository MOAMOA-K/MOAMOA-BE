package com.example.BE.receipt.domain.dto;

import com.example.BE.receipt.domain.ReceiptEntity;
import com.example.BE.receipt.domain.ReceiptEntity.ReceiptStatus;
import java.time.LocalDateTime;

public record ReceiptResponse(
    Long id,
    String storeName,
    Long totalPrice,
    ReceiptStatus status,
    LocalDateTime createdAt
) {
    public static ReceiptResponse from(ReceiptEntity entity) {
        return new ReceiptResponse(
            entity.getId(),
            entity.getStoreName(),
            entity.getTotalPrice(),
            entity.getStatus(),
            entity.getIssuedAt()
        );
    }
}
