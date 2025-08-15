package com.example.BE.user.domain;

import com.example.BE.auth.domain.SignupRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    public UserEntity(String email, String password, String nickname, UserRole role, Long points, PasswordEncoder passwordEncoder) {
        this.email = email;
        this.password = passwordEncoder.encode(password);
        this.nickname = nickname;
        this.role = role;
        this.points = points;
    }

    // 현재는 nickname만 수정이 가능합니다.
    public void updateNickname(String nickname) {
        if (nickname != null && !nickname.isBlank()) {
            this.nickname = nickname;
        }
    }

    public static UserEntity from(SignupRequest signupRequest, PasswordEncoder passwordEncoder){
        return new UserEntity(
            signupRequest.email(),
            signupRequest.password(),
            signupRequest.nickname(),
            signupRequest.role(),
            0L,
            passwordEncoder
        );
    }
}