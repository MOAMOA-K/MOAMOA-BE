package com.example.BE.user.domain.dto;


public record MessageResponse(
    String message
){
    public static MessageResponse from(String message){
        return new MessageResponse(message);
    }
}
