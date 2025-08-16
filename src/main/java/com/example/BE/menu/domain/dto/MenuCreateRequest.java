package com.example.BE.menu.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record MenuCreateRequest(
    @NotNull(message = "가게ID는 필수값입니다.")
    @Positive(message = "가게ID는 1이상입니다.")
    Long storeId,

    @NotBlank(message = "메뉴명은 필수값입니다.")
    String name,

    @PositiveOrZero(message = "가격은 0원 이상이어야 합니다.")
    int price,
    String description,
    String imageUrl
) {}