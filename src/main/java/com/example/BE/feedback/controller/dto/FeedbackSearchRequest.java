package com.example.BE.feedback.controller.dto;

import com.example.BE.feedback.domain.FeedbackStatus;
import com.example.BE.feedback.domain.FeedbackType;
import org.springframework.data.domain.Pageable;

public record FeedbackSearchRequest(
        Long userId,
        Long storeId,
        FeedbackType type,
        FeedbackStatus status,
        Pageable pageable
) {

    public static FeedbackSearchRequest of(
            Long userId,
            Long storeId,
            FeedbackType type,
            FeedbackStatus status,
            Pageable pageable
    ) {
        return new FeedbackSearchRequest(userId, storeId, type, status, pageable);
    }

}
