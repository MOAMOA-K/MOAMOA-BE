package com.example.BE.feedback.controller;

import com.example.BE.feedback.controller.dto.FeedbackCreateRequest;
import com.example.BE.feedback.controller.dto.FeedbackSearchRequest;
import com.example.BE.feedback.domain.FeedbackStatus;
import com.example.BE.feedback.domain.FeedbackType;
import com.example.BE.feedback.service.FeedbackService;
import com.example.BE.user.domain.dto.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackApiController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<?> submitFeedback(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody FeedbackCreateRequest request) {
        feedbackService.createFeedback(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.from("피드백이 등록되었습니다."));
    }

    @GetMapping("/{feedbackId}")
    public ResponseEntity<?> getFeedback(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long feedbackId) {
        var response = feedbackService.getFeedback(feedbackId);

        return ResponseEntity.ok(response);
    }

    //TODO: ADMIN 권한이 있는 사용자만 접근 가능하도록 수정 필요
    // 현재는 모든 사용자에게 열려있음
    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long storeId,
            @RequestParam(required = false) FeedbackType type,
            @RequestParam(required = false) FeedbackStatus status,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        var response = feedbackService.searchFeedback(
                FeedbackSearchRequest.of(
                        userId,
                        storeId,
                        type,
                        status,
                        pageable
                ));

        return ResponseEntity.ok(response);

    }

    @GetMapping("/my")
    public ResponseEntity<?> myFeedbacks(
            @AuthenticationPrincipal Long userId
    ) {
        var response = feedbackService.getMyFeedbacks(userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{feedbackId}/reply")
    public ResponseEntity<?> replyFeedback(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long feedbackId,
            @RequestBody String replyContent
    ) {
        feedbackService.replyFeedback(feedbackId, replyContent, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.from("피드백에 대한 답변이 등록되었습니다."));
    }

}
