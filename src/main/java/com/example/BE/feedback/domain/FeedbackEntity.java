package com.example.BE.feedback.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "feedbacks")
@Getter
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedbackEntity {

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

    @Builder
    public FeedbackEntity(Long userId, Long storeId, Long receiptId,
            Integer rating, String content, String modifiedContent) {
        this.userId = userId;
        this.storeId = storeId;
        this.receiptId = receiptId;
        this.rating = rating;
        this.content = content;
        this.modifiedContent = modifiedContent;
    }
}
