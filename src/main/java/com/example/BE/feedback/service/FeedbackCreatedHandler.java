package com.example.BE.feedback.service;

import com.example.BE.feedback.domain.event.FeedbackCreatedEvent;
import com.example.BE.feedback.repository.FeedbackRepository;
import com.example.BE.infra.openai.AiRewritePort;
import com.example.BE.infra.openai.dto.AiFeedbackResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onFeedbackCreated(FeedbackCreatedEvent event) {
        feedbackRepository.findById(event.feedbackId()).ifPresent(feedback -> {
            if (feedback.getModifiedContent() != null && !feedback.getModifiedContent().isBlank()) {
                return;
            }

            try {
                String aiResponse = sanitizeJson(aiRewritePort.rewrite(feedback.getContent()));
                log.info(aiResponse);
                ObjectMapper objectMapper = new ObjectMapper();
                AiFeedbackResponse response = objectMapper.readValue(aiResponse,
                        AiFeedbackResponse.class);

                feedback.updateModifiedContent(response);
            } catch (Exception e) {
                log.error("AI 정제 실패 id={}", feedback.getId(), e);

            }
        });
    }


    private String sanitizeJson(String response) {
        String sanitizedResponse = response.trim();
        if (sanitizedResponse.startsWith("```json")) {
            sanitizedResponse = sanitizedResponse.substring(7, sanitizedResponse.length() - 3)
                    .trim();
        }
        return sanitizedResponse;
    }
}

