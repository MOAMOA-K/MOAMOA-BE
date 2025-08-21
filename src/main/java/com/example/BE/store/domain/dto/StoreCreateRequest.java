package com.example.BE.store.domain.dto;

import com.example.BE.store.domain.StoreEntity.StoreCategory;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StoreCreateRequest(
    @NotBlank(message = "가게 이름은 필수입니다.")
    @Size(max=32, message = "가게명은 최대 32자까지 가능합니다.")
    String name,
    @NotBlank(message = "가게 주소는 필수입니다.")
    @Size(max=255, message = "주소명은 최대 255자까지 가능합니다.")
    String address,

    @NotNull(message = "위도 정보는 필수입니다.")
    @DecimalMin(value = "-90.0")  @DecimalMax(value = "90.0")
    Double latitude,

    @NotNull(message = "경도 정보는 필수입니다.")
    @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0")
    Double longitude,
    String description,

    @NotNull(message = "카테고리 정보는 필수입니다.")
    StoreCategory category,
    String imageUrl,

    @Size(max=255, message = "영업시간 입력은 최대 255자까지 가능합니다.")
    String openingTime
) {}
