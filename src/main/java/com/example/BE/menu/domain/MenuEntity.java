package com.example.BE.menu.domain;

import com.example.BE.menu.domain.dto.MenuCreateRequest;
import com.example.BE.menu.domain.dto.MenuUpdateRequest;
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

    public static MenuEntity from(MenuCreateRequest request){
        return new MenuEntity(
            request.storeId(),
            request.name(),
            request.price(),
            request.description(),
            request.imageUrl()
        );
    }

    // 메뉴 정보 수정
    public void update(MenuUpdateRequest request) {
        if (request.name() != null) this.name = request.name();
        if (request.price()!=null && request.price() >= 0) this.price = request.price();
        if (request.description() != null) this.description = request.description();
        if (request.imageUrl() != null) this.imageUrl = request.imageUrl();
    }
}