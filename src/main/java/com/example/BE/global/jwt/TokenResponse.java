package com.example.BE.global.jwt;

public record TokenResponse(
    String accessToken
) {
    public static TokenResponse from(String accessToken){
        return new TokenResponse(accessToken);
    }
}
