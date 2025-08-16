package com.example.BE.menu.domain.dto;

import jakarta.validation.constraints.PositiveOrZero;

public record MenuUpdateRequest(
    String name,
    // 수정 요청 시에 null을 받을 수 있도록 Wrapper 사용.(의도함)
    @PositiveOrZero(message = "가격은 0원 이상이어야 합니다.")
    Integer price,
    String description,
    String imageUrl
) {}