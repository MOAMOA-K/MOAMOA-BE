package com.example.BE.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
            .httpStatus(e.getHttpStatus())
            .errorCode(e.getErrorCode())
            .message(e.getMessage()).build();

        return ResponseEntity.status(e.getHttpStatus())
            .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        ErrorResponse errorResponse = ErrorResponse.builder()
            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            .errorCode("E999")
            .message(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorResponse);
    }

}
