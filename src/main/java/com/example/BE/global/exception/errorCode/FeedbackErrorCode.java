package com.example.BE.global.exception.errorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum FeedbackErrorCode implements ErrorCode {

    FEEDBACK_NOT_FOUND(HttpStatus.NOT_FOUND, "F001", "존재하지 않는 피드백입니다."),
    FORBIDDEN_FEEDBACK_ACCESS(HttpStatus.FORBIDDEN, "F002", "해당 피드백에 대한 접근 권한이 없습니다."),
    FEEDBACK_ALREADY_REPLIED(HttpStatus.BAD_REQUEST, "F003", "이미 답변이 등록된 피드백입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public HttpStatus httpStatus() {
        return this.httpStatus;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }
}
