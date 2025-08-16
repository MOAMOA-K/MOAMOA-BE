package com.example.BE.menu.domain.dto;

public record MenuCreateRequest(
    Long storeId,
    String name,
    int price,
    String description,
    String imageUrl
) {}