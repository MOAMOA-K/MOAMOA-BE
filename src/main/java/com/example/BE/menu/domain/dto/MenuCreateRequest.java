package com.example.BE.menu.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record MenuCreateRequest(
    @NotNull(message = "가게ID는 필수값입니다.")
    @Positive(message = "가게ID는 1이상입니다.")
    Long storeId,

    @NotBlank(message = "메뉴명은 필수값입니다.")
    @Size(max=32, message="메뉴명은 최대 32자입니다.")
    String name,

    @PositiveOrZero(message = "가격은 0원 이상이어야 합니다.")
    int price,

    @Size(max=255, message="메뉴 설명은 최대 255자입니다.")
    String description,
    String imageUrl
) {}