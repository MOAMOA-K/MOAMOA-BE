package com.example.BE.global.exception.errorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CouponErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "존재하지 않는 쿠폰입니다."),
    EXPIRED(HttpStatus.BAD_REQUEST, "C002", "이미 사용된 쿠폰입니다.");

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
