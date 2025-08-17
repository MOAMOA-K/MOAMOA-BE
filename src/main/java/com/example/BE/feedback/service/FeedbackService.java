package com.example.BE.feedback.service;

import com.example.BE.feedback.controller.dto.FeedbackCreateRequest;
import com.example.BE.feedback.controller.dto.FeedbackDetailResponse;
import com.example.BE.feedback.controller.dto.FeedbackOwnerResponse;
import com.example.BE.feedback.controller.dto.FeedbackResponse;
import com.example.BE.feedback.controller.dto.FeedbackSearchRequest;
import com.example.BE.feedback.domain.FeedbackEntity;
import com.example.BE.feedback.domain.event.FeedbackCreatedEvent;
import com.example.BE.feedback.repository.FeedbackRepository;
import com.example.BE.global.exception.CustomException;
import com.example.BE.global.exception.errorCode.FeedbackErrorCode;
import com.example.BE.global.exception.errorCode.UserErrorCode;
import com.example.BE.global.tx.EventTxPublisher;
import com.example.BE.receipt.domain.ReceiptEntity;
import com.example.BE.receipt.service.ReceiptService;
import com.example.BE.user.domain.UserEntity;
import com.example.BE.user.repository.UserRepository;
import com.example.BE.user.service.UserService;
import java.util.List;
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
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    public void createFeedback(FeedbackCreateRequest request, Long userId) {
        ReceiptEntity receiptEntity = receiptService.validateReceipt(request.receiptId());

        FeedbackEntity feedback = request.toEntity(userId, receiptEntity);
        feedbackRepository.save(feedback);

        addUserPoints(userId);

        eventPublisher.publish(new FeedbackCreatedEvent(feedback.getId()));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public FeedbackResponse getFeedback(Long feedbackId) {
        FeedbackEntity feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> CustomException.from(FeedbackErrorCode.FEEDBACK_NOT_FOUND));

        return FeedbackResponse.from(feedback);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Page<FeedbackOwnerResponse> searchFeedback(FeedbackSearchRequest request) {
        return feedbackRepository.search(request);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<FeedbackDetailResponse> getMyFeedbacks(Long userId) {
        List<FeedbackDetailResponse> feedbacks = feedbackRepository.findMyFeedbacks(userId);
        if (feedbacks.isEmpty()) {
            throw CustomException.from(FeedbackErrorCode.FEEDBACK_NOT_FOUND);
        }

        return feedbacks;
    }

    @Transactional
    public void replyFeedback(Long feedbackId, String replyContent, Long userId) {
        FeedbackEntity feedback = feedbackRepository.findOwnedFeedback(feedbackId, userId)
                .orElseThrow(() -> CustomException.from(FeedbackErrorCode.FEEDBACK_NOT_FOUND));

        if (feedback.getReply() != null) {
            throw CustomException.from(FeedbackErrorCode.FEEDBACK_ALREADY_REPLIED);
        }

        feedback.reply(replyContent);
        feedbackRepository.save(feedback);
    }

    private void addUserPoints(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> CustomException.from(UserErrorCode.NOT_FOUND));

        user.addPoints(300L);
    }
}
