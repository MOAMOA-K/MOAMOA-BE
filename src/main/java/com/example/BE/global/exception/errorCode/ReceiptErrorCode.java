package com.example.BE.global.exception.errorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ReceiptErrorCode implements ErrorCode {
    RECEIPT_NOT_FOUND(HttpStatus.NOT_FOUND, "R001", "존재하지 않는 영수증입니다."),
    RECEIPT_NOT_AUTHORIZED(HttpStatus.BAD_REQUEST, "R002", "인증되지 않은 영수증입니다."),
    OCR_REQUEST_FAILED(HttpStatus.BAD_REQUEST, "R003", "영수증을 다시 인식해 주세요."),
    FORBIDDEN_RECEIPT_ACCESS(HttpStatus.FORBIDDEN, "R004", "해당 영수증에 대한 접근 권한이 없습니다.");

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
