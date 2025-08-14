package com.example.BE.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
            .errorCode(e.getErrorCode())
            .message(e.getMessage()).build();

        return ResponseEntity.status(e.getHttpStatus())
            .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        log.error("Unexpected Error: ", e);
        ErrorResponse errorResponse = ErrorResponse.builder()
            .errorCode("E999")
            .message("서버 내부 오류입니다.").build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorResponse);
    }

}
