package com.example.BE.menu.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MenuCreateRequest(
    @NotNull(message = "가게ID는 필수값입니다.")
    Long storeId,

    @NotBlank(message = "가게명은 필수값입니다.")
    String name,
    int price,
    String description,
    String imageUrl
) {}