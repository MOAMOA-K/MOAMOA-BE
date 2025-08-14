package com.example.BE.global.exception.errorCode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus httpStatus();

    String code();

    String message();

}
