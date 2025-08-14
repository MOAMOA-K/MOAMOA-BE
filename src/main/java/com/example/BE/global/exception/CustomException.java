package com.example.BE.global.exception;

import com.example.BE.global.exception.errorCode.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String errorCode;

    private CustomException(ErrorCode errorCode) {
        // detailMessage field로 message를 전달합니다.
        super(errorCode.message());
        this.httpStatus = errorCode.httpStatus();
        this.errorCode = errorCode.code();
    }

    public static CustomException from(ErrorCode errorCode) {
        return new CustomException(errorCode);
    }

}
