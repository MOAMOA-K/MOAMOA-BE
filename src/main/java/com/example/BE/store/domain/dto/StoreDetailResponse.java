package com.example.BE.store.domain.dto;

import com.example.BE.store.domain.StoreEntity.StoreCategory;

public record StoreDetailResponse(
    // 가게 정보, 메뉴 리스트, 개선사항(StoreAnnouncement) 리스트를 포함
    // 이전 답변에서 설계한 DTO 구조를 활용
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
    //List<MenuResponse>,
    //List<StoreAnnouncementResponse>
) {}
