package com.example.BE.feedback.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record FeedbackDetailResponse(
        Long id,
        String storeName,
        Integer rating,
        String content,
        String reply,
        String improvements,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime createdAt
) {

}
