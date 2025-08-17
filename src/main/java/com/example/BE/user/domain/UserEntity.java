package com.example.BE.user.domain;

import com.example.BE.auth.domain.SignupRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "users")
@Getter
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", length = 32, nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "nickname", length = 16, nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20, nullable = false)
    private UserRole role;

    @Column(name = "points", nullable = false)
    private Long points;

    private UserEntity(String email, String password, String nickname, UserRole role, Long points) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.points = points;
    }

    public static UserEntity from(SignupRequest signupRequest, PasswordEncoder passwordEncoder) {
        return new UserEntity(
            signupRequest.email(),
            passwordEncoder.encode(signupRequest.password()),
            signupRequest.nickname(),
            signupRequest.role(),
            0L
        );
    }

    // 현재는 nickname만 수정이 가능합니다.
    public void updateNickname(String nickname) {
        if (nickname != null && !nickname.isBlank()) {
            this.nickname = nickname;
        }
    }

    public enum UserRole {
        ROLE_CUSTOMER, ROLE_OWNER, ROLE_ADMIN
    }
}