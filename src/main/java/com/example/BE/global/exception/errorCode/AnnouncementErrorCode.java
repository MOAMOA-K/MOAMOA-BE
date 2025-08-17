package com.example.BE.global.exception.errorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AnnouncementErrorCode implements ErrorCode {
    
    ANNOUNCEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "A001", "존재하지 않는 개선사항입니다."),
    FORBIDDEN_ANNOUNCEMENT_ACCESS(HttpStatus.FORBIDDEN, "A002", "해당 개선사항에 대한 접근 권한이 없습니다.");

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
