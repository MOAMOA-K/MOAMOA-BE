package com.example.BE.feedback.domain;

import com.example.BE.global.base.BaseTimeEntity;
import com.example.BE.infra.openai.dto.AiFeedbackResponse;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "feedbacks")
@Getter
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedbackEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "receipt_id")
    private Long receiptId;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "content", length = 500)
    private String content;

    @Column(name = "modified_content", length = 500)
    private String modifiedContent;

    @Column(name = "improvements", length = 500)
    private String improvements;

    @Column(name = "reply", length = 255)
    private String reply;

    @Enumerated(EnumType.STRING)
    private FeedbackStatus status;

    @Enumerated(EnumType.STRING)
    private FeedbackType type;


    @Builder
    public FeedbackEntity(
            Long userId,
            Long storeId,
            Long receiptId,
            Integer rating,
            String content,
            FeedbackType type
    ) {
        this.userId = userId;
        this.storeId = storeId;
        this.receiptId = receiptId;
        this.rating = rating;
        this.content = content;
        this.modifiedContent = null;
        this.improvements = null;
        this.reply = null;
        this.status = FeedbackStatus.UNREAD;
        this.type = type;
    }

    public void updateModifiedContent(AiFeedbackResponse response) {
        this.modifiedContent = response.modifiedContent();
        this.improvements = response.improvements();
    }

    public void reply(String reply) {
        this.reply = reply;
        this.status = FeedbackStatus.DONE;
    }
}
