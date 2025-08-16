package com.example.BE.store.domain.dto;

import com.example.BE.store.domain.StoreEntity.StoreCategory;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StoreCreateRequest(
    @NotBlank(message = "가게 이름은 필수입니다.")
    String name,
    @NotBlank(message = "가게 주소는 필수입니다.")
    String address,

    @NotNull(message = "위도 정보는 필수입니다.")
    @DecimalMin(value = "-90.0")  @DecimalMax(value = "90.0")
    Double latitude,

    @NotNull(message = "경도 정보는 필수입니다.")
    @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0")
    Double longitude,
    String description,

    @NotBlank(message = "카테고리 정보는 필수입니다.")
    StoreCategory category,
    String imageUrl,
    String openingTime
) {}
