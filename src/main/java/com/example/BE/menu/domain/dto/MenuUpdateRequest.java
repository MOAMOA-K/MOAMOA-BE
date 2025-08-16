package com.example.BE.menu.domain.dto;

public record MenuUpdateRequest(
    String name,
    Integer price, // int 대신 Integer를 사용하여 null 체크 -> 애매함
    String description,
    String imageUrl
) {}