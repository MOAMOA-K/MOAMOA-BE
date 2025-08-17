package com.example.BE.receipt.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReceiptStatus {
    PENDING("승인 대기"),
    AVAILABLE("사용 가능"),
    EXPIRED("만료됨");

    private final String label;
}
