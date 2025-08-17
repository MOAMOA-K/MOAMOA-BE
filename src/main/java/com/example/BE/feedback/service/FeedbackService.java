package com.example.BE.feedback.service;

import com.example.BE.feedback.controller.dto.FeedbackCreateRequest;
import com.example.BE.feedback.domain.FeedbackEntity;
import com.example.BE.feedback.repository.FeedbackRepository;
import com.example.BE.receipt.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ReceiptService receiptService;

    @Transactional
    public void createFeedback(FeedbackCreateRequest request, Long userId) {
        // 영수증 유효 검사
        receiptService.validateReceipt(request.receiptId());

        // toEntity 메서드 호출
        FeedbackEntity feedback = request.toEntity(userId);
        feedbackRepository.save(feedback);

        // ai에게 요청 보내기
    }


}
