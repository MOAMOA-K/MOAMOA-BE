package com.example.BE.feedback.service;

import com.example.BE.feedback.domain.event.FeedbackCreatedEvent;
import com.example.BE.feedback.repository.FeedbackRepository;
import com.example.BE.infra.openai.AiRewritePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeedbackCreatedHandler {

    private final FeedbackRepository feedbackRepository;
    private final AiRewritePort aiRewritePort;

    @Async("feedbackExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void onFeedbackCreated(FeedbackCreatedEvent event) {
        feedbackRepository.findById(event.feedbackId()).ifPresent(feedback -> {
            if (feedback.getModifiedContent() != null && !feedback.getModifiedContent().isBlank()) {
                return;
            }

            try {
                String revised = aiRewritePort.rewrite(feedback.getContent());
                feedback.updateModifiedContent(revised);
            } catch (Exception e) {
                log.info("AI 정제 실패 id={}", feedback.getId(), e);

            }
        });
    }
}
