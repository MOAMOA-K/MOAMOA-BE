package com.example.BE.store.domain.dto;

import com.example.BE.announcement.domain.dto.AnnouncementResponse;
import com.example.BE.menu.domain.dto.MenuResponse;
import com.example.BE.store.domain.StoreEntity.StoreCategory;
import java.util.List;

public record StoreDetailResponse(
    Long id,
    String name,
    String canonicalName,
    String address,
    Double latitude,
    Double longitude,
    String description,
    StoreCategory category,
    String imageUrl,
    String openingTime,
    List<MenuResponse> menuList,
    List<AnnouncementResponse> announcementList
) {

    public static StoreDetailResponse from(StoreResponse storeResponse,
        List<MenuResponse> menuResponseList, List<AnnouncementResponse> announcementList) {
        return new StoreDetailResponse(
            storeResponse.id(),
            storeResponse.name(),
            storeResponse.canonicalName(),
            storeResponse.address(),
            storeResponse.latitude(),
            storeResponse.longitude(),
            storeResponse.description(),
            storeResponse.category(),
            storeResponse.imageUrl(),
            storeResponse.openingTime(),
            menuResponseList,
            announcementList
        );
    }
}
