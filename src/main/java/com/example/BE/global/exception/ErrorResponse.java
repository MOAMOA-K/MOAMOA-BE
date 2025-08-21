package com.example.BE.global.exception;

import lombok.Builder;

@Builder
public record ErrorResponse (
    String errorCode,
    String message
){
}
