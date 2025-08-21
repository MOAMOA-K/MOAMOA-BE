package com.example.BE.global.exception.errorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "존재하지 않는 유저입니다."),
    INVALID_EMAIL_PASSWORD(HttpStatus.UNAUTHORIZED, "U002", "잘못된 이메일, 비밀번호 조합입니다."),
    REQUIRED_LOGIN(HttpStatus.UNAUTHORIZED, "U003", "로그인이 필요합니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "U004", "이미 존재하는 이메일입니다."),
    NOT_ADMIN(HttpStatus.FORBIDDEN, "U005", "어드민이 아닙니다."),
    NOT_ALLOWED_ADMIN(HttpStatus.FORBIDDEN, "U006", "어드민 계정 생성은 관리자에게 문의해주세요"),
    POINT_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "U007", "포인트가 부족합니다.");

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
