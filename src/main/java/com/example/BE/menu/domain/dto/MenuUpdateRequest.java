package com.example.BE.menu.domain.dto;

public record MenuUpdateRequest(
    String name,
    // 수정 요청 시에 null을 받을 수 있도록 Wrapper 사용.(의도함)
    Integer price,
    String description,
    String imageUrl
) {}