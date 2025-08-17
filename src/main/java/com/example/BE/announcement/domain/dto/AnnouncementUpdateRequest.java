package com.example.BE.announcement.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AnnouncementUpdateRequest(
    @NotNull(message = "피드백ID는 필수값입니다.")
    @Positive(message = "피드백ID는 1이상입니다.")
    Long feedbackId,

    @Size(max=500, message="개선사항 설명은 최대 500자입니다.")
    String description
) {}