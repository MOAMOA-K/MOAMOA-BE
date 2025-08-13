package com.example.BE.store.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store")
@Getter
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "category", length = 128)
    private String category;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Builder
    public StoreEntity(Long userId, String name, String address, String description,
            String category, String imageUrl) {
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
    }
}