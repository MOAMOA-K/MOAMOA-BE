package com.example.BE.feedback.service;

import com.example.BE.feedback.controller.dto.FeedbackCreateRequest;
import com.example.BE.feedback.domain.FeedbackEntity;
import com.example.BE.feedback.domain.event.FeedbackCreatedEvent;
import com.example.BE.feedback.repository.FeedbackRepository;
import com.example.BE.global.tx.EventTxPublisher;
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
    private final EventTxPublisher eventPublisher;

    @Transactional
    public void createFeedback(FeedbackCreateRequest request, Long userId) {
        receiptService.validateReceipt(request.receiptId());

        FeedbackEntity feedback = request.toEntity(userId);
        feedbackRepository.save(feedback);

        eventPublisher.publish(new FeedbackCreatedEvent(feedback.getId()));
    }


}
