package com.example.BE.user.service;

import com.example.BE.auth.domain.LoginRequest;
import com.example.BE.auth.domain.SignupRequest;
import com.example.BE.global.exception.CustomException;
import com.example.BE.global.exception.errorCode.UserErrorCode;
import com.example.BE.global.jwt.JwtUtil;
import com.example.BE.global.jwt.TokenResponse;
import com.example.BE.user.domain.UserEntity;
import com.example.BE.user.domain.dto.UserResponse;
import com.example.BE.user.domain.dto.UserUpdateRequest;
import com.example.BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 단일 유저 조회
    @Transactional
    public UserResponse findById(Long userId) {
        UserEntity user = findByIdOrThrow(userId);
        return UserResponse.from(user);
    }

    // 유저 프로필 수정
    @Transactional
    public UserResponse updateProfile(Long userId, UserUpdateRequest request) {
        UserEntity user = findByIdOrThrow(userId);
        user.updateNickname(request.nickname());

        return UserResponse.from(user);
    }

    // 유저 삭제
    @Transactional
    public void deleteUser(Long userId) {
        findByIdOrThrow(userId);
        userRepository.deleteById(userId);
    }

    // 회원가입
    @Transactional
    public UserResponse signUpUser(SignupRequest signupRequest){
        if(userRepository.existsByEmail(signupRequest.email())){
            throw CustomException.from(UserErrorCode.DUPLICATE_EMAIL);
        }
        UserEntity user = UserEntity.from(signupRequest, passwordEncoder);
        return UserResponse.from(userRepository.save(user));
    }

    // 로그인
    @Transactional
    public TokenResponse login(LoginRequest loginRequest){
        UserEntity user = userRepository.findByEmail(loginRequest.email())
            .orElseThrow(()->CustomException.from(UserErrorCode.NOT_FOUND));

        // 비밀번호 일치하지 않음
        if(!passwordEncoder.matches(loginRequest.password(), user.getPassword())){
            throw CustomException.from(UserErrorCode.INVALID_EMAIL_PASSWORD);
        }

        return TokenResponse.from(jwtUtil.generateToken(
            user.getId(),
            user.getEmail(),
            user.getRole()
        ));
    }

    // 로그아웃
    public void logout(String email){
        log.info("logout reqeust by : {}", email);
    }

    private UserEntity findByIdOrThrow(Long userId){
        return userRepository.findById(userId)
            .orElseThrow(()->CustomException.from(UserErrorCode.NOT_FOUND));
    }
}