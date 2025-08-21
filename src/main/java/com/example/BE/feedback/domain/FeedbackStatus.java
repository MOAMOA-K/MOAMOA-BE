package com.example.BE.feedback.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeedbackStatus {
    UNREAD("읽지 않음"),
    DONE("완료");

    private final String description;
}
