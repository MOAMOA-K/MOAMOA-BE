package com.example.BE.menu.domain; // 패키지 경로는 프로젝트에 맞게 조정하세요.

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Builder
    public MenuEntity(Long storeId, String name, int price, String description, String imageUrl) {
        this.storeId = storeId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // 메뉴 정보 수정
    public void update(String name, int price, String description, String imageUrl) {
        if (name != null) this.name = name;
        if (price > 0) this.price = price; // 가격은 0보다 클 때만 수정되도록 설계
        if (description != null) this.description = description;
        if (imageUrl != null) this.imageUrl = imageUrl;
    }
}