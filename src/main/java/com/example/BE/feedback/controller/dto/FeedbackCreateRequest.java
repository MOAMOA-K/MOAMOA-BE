package com.example.BE.feedback.controller.dto;

import com.example.BE.feedback.domain.FeedbackEntity;
import com.example.BE.feedback.domain.FeedbackType;
import com.example.BE.receipt.domain.ReceiptEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FeedbackCreateRequest(
        @NotNull(message = "영수증 ID는 필수입니다.")
        Long receiptId,

        @NotNull(message = "평점은 필수입니다.")
        @Min(value = 1, message = "평점은 최소 1점 이상이어야 합니다.")
        @Max(value = 5, message = "평점은 최대 5점까지 입력 가능합니다.")
        Integer rating,

        @NotBlank(message = "리뷰 내용을 입력해주세요.")
        String content,

        @NotNull(message = "피드백 유형을 선택해야 합니다.")
        FeedbackType type
) {

    public FeedbackEntity toEntity(Long userId, ReceiptEntity receipt) {
        return FeedbackEntity.builder()
                .userId(userId)
                .receiptId(receiptId)
                .storeId(receipt.getStoreId())
                .rating(rating)
                .content(content)
                .type(type)
                .build();
    }
}