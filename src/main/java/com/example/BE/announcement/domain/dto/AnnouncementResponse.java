package com.example.BE.announcement.domain.dto;

import com.example.BE.announcement.domain.AnnouncementEntity;

public record AnnouncementResponse(
    Long id,
    Long storeId,
    Long feedbackId,
    String feedbackContent,
    String description
) {

    // parameter로 dto나 entity가 아니라 필드값을 하나씩 주면 of method라고 명명하라고 했던 것 같아서
    // 메서드 이름을 이렇게 지어봤어요(의도함)
    public static AnnouncementResponse of(AnnouncementEntity entity, String feedbackContent) {
        return new AnnouncementResponse(
            entity.getId(),
            entity.getStoreId(),
            entity.getFeedbackId(),
            feedbackContent,
            entity.getDescription()
        );
    }
}