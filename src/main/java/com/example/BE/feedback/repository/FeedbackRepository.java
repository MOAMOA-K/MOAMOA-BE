package com.example.BE.feedback.repository;

import com.example.BE.feedback.controller.dto.FeedbackDetailResponse;
import com.example.BE.feedback.domain.FeedbackEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long>,
        FeedbackCustomRepository {

    List<FeedbackEntity> findAllByIdIn(List<Long> feedbackIds);

    List<FeedbackEntity> findAllByStoreId(Long storeId);

    @Query("""
            SELECT new com.example.BE.feedback.controller.dto.FeedbackDetailResponse(
                f.id,
                s.name,
                f.rating,
                f.content,
                f.reply,
                f.improvements,
                f.createdAt
            )
            FROM FeedbackEntity f
            JOIN StoreEntity s ON f.storeId = s.id
            WHERE f.userId = :userId
            ORDER BY f.createdAt DESC
            """)
    List<FeedbackDetailResponse> findMyFeedbacks(Long userId);


    @Query("""
                select f 
                from FeedbackEntity f
                  join StoreEntity s on s.id = f.storeId
                where f.id = :feedbackId
                  and s.userId = :ownerId
            """)
    Optional<FeedbackEntity> findOwnedFeedback(Long feedbackId, Long ownerId);
}
