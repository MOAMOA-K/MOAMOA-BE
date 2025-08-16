package com.example.BE.store.domain.dto;

import com.example.BE.store.domain.StoreEntity;
import com.example.BE.store.domain.StoreEntity.StoreCategory;

public record StoreResponse(
    Long id,
    String name,
    String canonicalName,
    String address,
    Double latitude,
    Double longitude,
    String description,
    StoreCategory category,
    String imageUrl,
    String openingTime
) {
    public static StoreResponse from(StoreEntity store) {
        return new StoreResponse(
            store.getId(),
            store.getName(),
            store.getCanonicalName(),
            store.getAddress(),
            store.getLatitude(),
            store.getLongitude(),
            store.getDescription(),
            store.getCategory(),
            store.getImageUrl(),
            store.getOpeningTime()
        );
    }
}


