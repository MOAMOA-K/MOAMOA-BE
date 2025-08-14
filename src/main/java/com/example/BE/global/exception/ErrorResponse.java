package com.example.BE.global.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ErrorResponse (
    HttpStatus httpStatus,
    String errorCode,
    String message
){
}
