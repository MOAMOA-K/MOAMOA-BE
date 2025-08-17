package com.example.BE.feedback.controller.dto;

import com.example.BE.feedback.domain.FeedbackEntity;
import com.example.BE.feedback.domain.FeedbackStatus;
import com.example.BE.feedback.domain.FeedbackType;

public record FeedbackResponse(
        Long id,
        Long userId,
        Long storeId,
        Long receiptId,
        Integer rating,
        String content,
        String modifiedContent,
        String reply,
        FeedbackType type,
        FeedbackStatus status
) {

    public static FeedbackResponse from(FeedbackEntity entity) {
        return new FeedbackResponse(
                entity.getId(),
                entity.getUserId(),
                entity.getStoreId(),
                entity.getReceiptId(),
                entity.getRating(),
                entity.getContent(),
                entity.getModifiedContent(),
                entity.getReply(),
                entity.getType(),
                entity.getStatus()
        );
    }
}
