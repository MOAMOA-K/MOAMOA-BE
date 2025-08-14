package com.example.BE.user.service;

import com.example.BE.global.exception.CustomException;
import com.example.BE.global.exception.errorCode.UserErrorCode;
import com.example.BE.user.domain.UserEntity;
import com.example.BE.user.domain.dto.UserResponse;
import com.example.BE.user.domain.dto.UserUpdateRequest;
import com.example.BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    // 단일 유저 조회
    public UserResponse findById(Long userId) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> CustomException.from(UserErrorCode.NOT_FOUND));
        return UserResponse.from(user);
    }

    // 유저 프로필 수정
    public UserResponse updateProfile(Long userId, UserUpdateRequest request) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> CustomException.from(UserErrorCode.NOT_FOUND));

        user.updateNickname(request.nickname());

        return UserResponse.from(user);
    }

    // 유저 삭제
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw CustomException.from(UserErrorCode.NOT_FOUND);
        }
        userRepository.deleteById(userId);
    }
}