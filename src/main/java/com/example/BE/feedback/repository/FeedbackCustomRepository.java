package com.example.BE.feedback.repository;

import com.example.BE.feedback.controller.dto.FeedbackResponse;
import com.example.BE.feedback.controller.dto.FeedbackSearchRequest;
import org.springframework.data.domain.Page;

public interface FeedbackCustomRepository {

    Page<FeedbackResponse> search(
            FeedbackSearchRequest request
    );
}
