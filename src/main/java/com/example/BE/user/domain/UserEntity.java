package com.example.BE.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Getter
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

    public enum UserRole {
        ROLE_CUSTOMER, ROLE_OWNER, ROLE_ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", length = 32)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname", length = 16)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20)
    private UserRole role;

    @Column(name = "points")
    private Long points;

    @Builder
    public UserEntity(String email, String password, String nickname, UserRole role, Long points) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.points = points;
    }
}