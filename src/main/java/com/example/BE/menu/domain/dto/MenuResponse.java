package com.example.BE.menu.domain.dto;

import com.example.BE.menu.domain.MenuEntity;

public record MenuResponse(
    Long id,
    Long storeId,
    String name,
    int price,
    String description,
    String imageUrl
) {
    public static MenuResponse from(MenuEntity menu) {
        return new MenuResponse(
            menu.getId(),
            menu.getStoreId(),
            menu.getName(),
            menu.getPrice(),
            menu.getDescription(),
            menu.getImageUrl()
        );
    }
}