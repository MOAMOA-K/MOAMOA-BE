package com.example.BE.store.domain;

import com.example.BE.store.domain.dto.StoreCreateRequest;
import com.example.BE.store.domain.dto.StoreUpdateRequest;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stores")
@Getter
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "name", length = 20, nullable = false, unique = true)
    private String name;

    @Column(name = "canonical_name", length = 20)
    private String canonicalName;

    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "description", length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 128, nullable = false)
    private StoreCategory category;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "opening_time")
    private String openingTime;

    @Builder
    private StoreEntity(Long userId, String name, String canonicalName, String address,
        Double latitude, Double longitude, String description, StoreCategory category,
        String imageUrl, String openingTime) {
        this.userId = userId;
        this.name = name;
        this.canonicalName = canonicalName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
        this.openingTime = openingTime;
    }

    public static StoreEntity of(Long userId, StoreCreateRequest request) {
        return new StoreEntity(
            userId,
            request.name(),
            request.name().replaceAll("\\s+", ""),
            request.address(),
            request.latitude(),
            request.longitude(),
            request.description(),
            request.category(),
            request.imageUrl(),
            request.openingTime()
        );
    }

    // 정보 수정 메서드
    public void update(StoreUpdateRequest request) {
        if (request.name() != null) {
            this.name = request.name();
            this.canonicalName = request.name().replaceAll("\\s+", "");
        }
        if (request.address() != null) {
            this.address = request.address();
        }
        if (request.description() != null) {
            this.description = request.description();
        }
        if (request.latitude() != null) {
            this.latitude = request.latitude();
        }
        if (request.longitude() != null) {
            this.longitude = request.longitude();
        }
        if (request.category() != null) {
            this.category = request.category();
        }
        if (request.imageUrl() != null) {
            this.imageUrl = request.imageUrl();
        }
        if (request.openingTime() != null) {
            this.openingTime = request.openingTime();
        }
    }

    public enum StoreCategory {
        KOREAN,
        CHINESE,
        JAPANESE,
        WESTERN,
        CAFE,
        FASTFOOD,
        BAR,
        OTHERS
    }
}