package com.example.BE.store.domain;

import com.example.BE.store.domain.dto.StoreUpdateRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store")
@Builder
@Getter
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name="canonical_name", length=20, nullable = false)
    private String canonicalName;

    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @Column(name="latitude", nullable = false)
    private Double latitude;

    @Column(name="longitude", nullable = false)
    private Double longitude;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "category", length = 128, nullable = false)
    private StoreCategory category;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name="opening_time")
    private String openingTime;

    public enum StoreCategory{
        KOREAN,
        CHINESE,
        JAPANESE,
        WESTERN,
        CAFE,
        FASTFOOD,
        BAR,
        OTHERS
    }

    // 정보 수정 메서드
    public void update(StoreUpdateRequest request) {
        if (request.name() != null) {
            this.name = request.name();
            this.canonicalName = request.name().replaceAll("\\s+", "");
        }
        if (request.address() != null) this.address = request.address();
        if (request.description() != null) this.description = request.description();
        if(request.latitude()!=null)  this.latitude = request.latitude();
        if(request.longitude()!=null)  this.longitude= request.longitude();
        if (request.category() != null) this.category = request.category();
        if (request.imageUrl() != null) this.imageUrl = request.imageUrl();
        if (request.openingTime() != null) this.openingTime = request.openingTime();
    }
}