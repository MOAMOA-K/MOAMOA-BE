package com.example.BE.storeannouncement.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "StoreAnnouncement")
@Getter
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreAnnouncementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "feedback_id")
    private Long feedbackId;

    @Column(name = "content", length = 500)
    private String content;

    @Builder
    public StoreAnnouncementEntity(Long storeId, Long feedbackId, String content) {
        this.storeId = storeId;
        this.feedbackId = feedbackId;
        this.content = content;
    }
}
