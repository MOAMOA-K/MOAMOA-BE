package com.example.BE.feedback.controller.dto;

import com.example.BE.feedback.domain.FeedbackStatus;
import com.example.BE.feedback.domain.FeedbackType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record FeedbackOwnerResponse(
        Long id,
        Integer rating,
        String modifiedContent,
        String reply,
        FeedbackType type,
        FeedbackStatus status,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime createdAt
) {

}
