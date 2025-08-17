package com.example.BE.feedback.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeedbackType {
    COMPLIMENT("칭찬"),
    SUGGESTION("제안"),
    COMPLAINT("불만");

    private final String label;
}
