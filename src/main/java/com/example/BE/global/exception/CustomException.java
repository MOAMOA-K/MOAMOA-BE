package com.example.BE.global.exception;

import com.example.BE.global.exception.errorCode.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    private CustomException(ErrorCode errorCode) {
        super(errorCode.message());
        this.httpStatus = errorCode.httpStatus();
        this.errorCode = errorCode.code();
        this.errorMessage = errorCode.message();
    }

    public static CustomException from(ErrorCode errorCode) {
        return new CustomException(errorCode);
    }

}
