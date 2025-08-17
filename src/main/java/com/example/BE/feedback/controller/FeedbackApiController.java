package com.example.BE.feedback.controller;

import com.example.BE.feedback.controller.dto.FeedbackCreateRequest;
import com.example.BE.feedback.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackApiController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<?> submitFeedback(
            @AuthenticationPrincipal Long userId,
            @Valid FeedbackCreateRequest request) {
        feedbackService.createFeedback(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
