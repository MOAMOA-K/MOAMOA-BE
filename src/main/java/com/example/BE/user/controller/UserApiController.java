package com.example.BE.user.controller;

import com.example.BE.user.domain.dto.MessageResponse;
import com.example.BE.user.domain.dto.UserResponse;
import com.example.BE.user.domain.dto.UserUpdateRequest;
import com.example.BE.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(
        @PathVariable Long userId
    ){
        UserResponse userResponse = userService.findById(userId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(userResponse);
    }

    // 유저 프로필 수정
    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
        @PathVariable Long userId,
        @RequestBody UserUpdateRequest request
    ) {
        UserResponse userResponse = userService.updateProfile(userId, request);
        return ResponseEntity.status(HttpStatus.OK)
            .body(userResponse);
    }

    // 유저 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<MessageResponse> deleteUser(
        @PathVariable Long userId
    ) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(MessageResponse.from("회원 탈퇴가 완료되었습니다."));
    }


}
