package com.example.BE.announcement.domain;

import com.example.BE.announcement.domain.dto.AnnouncementCreateRequest;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "announcement")
@Getter
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnnouncementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "feedback_id", nullable = false)
    private Long feedbackId;

    @Column(name = "description", length = 500)
    private String description;

    @Builder
    public AnnouncementEntity(Long storeId, Long feedbackId, String description) {
        this.storeId = storeId;
        this.feedbackId = feedbackId;
        this.description = description;
    }

    public static AnnouncementEntity from(AnnouncementCreateRequest request){
        return new AnnouncementEntity(
            request.storeId(),
            request.feedbackId(),
            request.description()
        );
    }

    public void update(Long feedbackId, String description) {
        if (feedbackId != null) this.feedbackId = feedbackId;
        if (description != null) this.description = description;
    }
}
