package com.example.BE.store.domain.dto;

import com.example.BE.store.domain.StoreEntity.StoreCategory;

public record StoreUpdateRequest(
    String name,
    String address,
    Double latitude,
    Double longitude,
    String description,
    StoreCategory category,
    String imageUrl,
    String openingTime
) {}
