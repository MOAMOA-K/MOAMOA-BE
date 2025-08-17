package com.example.BE.feedback.repository;

import com.example.BE.feedback.domain.FeedbackEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long>,
        FeedbackCustomRepository {

    List<FeedbackEntity> findAllByIdIn(List<Long> feedbackIds);
}
