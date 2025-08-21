package com.example.BE.global.exception;

import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        log.warn("Handled CustomException: code={}, status={}, msg={}",
            e.getErrorCode(), e.getHttpStatus(), e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
            .errorCode(e.getErrorCode())
            .message(e.getMessage()).build();

        return ResponseEntity.status(e.getHttpStatus())
            .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.warn("Validation exception: status={}, msg={}",
            e.getStatusCode(), e.getMessage());

        // valid 조건을 여러 개 위배했을 수도 있음.
        // message 여러 개를 한 개의 String으로 변환
        String allErrorMessages = e.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining("\n"));


        ErrorResponse errorResponse = ErrorResponse.builder()
            .errorCode("V999")
            .message(allErrorMessages).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
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
