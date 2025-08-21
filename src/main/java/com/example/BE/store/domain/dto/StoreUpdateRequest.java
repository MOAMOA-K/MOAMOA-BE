package com.example.BE.store.domain.dto;

import com.example.BE.store.domain.StoreEntity.StoreCategory;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

public record StoreUpdateRequest(
    String name,
    String address,

    @DecimalMin(value = "-90.0")  @DecimalMax(value = "90.0")
    Double latitude,

    @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0")
    Double longitude,
    String description,
    StoreCategory category,
    String imageUrl,
    String openingTime
) {}
