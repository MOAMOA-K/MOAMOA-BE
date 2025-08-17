package com.example.BE.feedback.service;

import com.example.BE.feedback.controller.dto.FeedbackCreateRequest;
import com.example.BE.feedback.controller.dto.FeedbackResponse;
import com.example.BE.feedback.controller.dto.FeedbackSearchRequest;
import com.example.BE.feedback.domain.FeedbackEntity;
import com.example.BE.feedback.domain.event.FeedbackCreatedEvent;
import com.example.BE.feedback.repository.FeedbackRepository;
import com.example.BE.global.exception.CustomException;
import com.example.BE.global.exception.errorCode.FeedbackErrorCode;
import com.example.BE.global.tx.EventTxPublisher;
import com.example.BE.receipt.domain.ReceiptEntity;
import com.example.BE.receipt.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
        ReceiptEntity receiptEntity = receiptService.validateReceipt(request.receiptId());

        FeedbackEntity feedback = request.toEntity(userId, receiptEntity);
        feedbackRepository.save(feedback);

        eventPublisher.publish(new FeedbackCreatedEvent(feedback.getId()));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public FeedbackResponse getFeedback(Long feedbackId) {
        FeedbackEntity feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> CustomException.from(FeedbackErrorCode.FEEDBACK_NOT_FOUND));

        return FeedbackResponse.from(feedback);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Page<FeedbackResponse> searchFeedback(FeedbackSearchRequest request) {
        return feedbackRepository.search(request);
    }


}
