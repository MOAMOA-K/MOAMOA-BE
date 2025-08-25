package com.example.BE.user.service;

import com.example.BE.global.exception.CustomException;
import com.example.BE.global.exception.errorCode.UserErrorCode;
import com.example.BE.user.domain.UserEntity;
import com.example.BE.user.domain.dto.UserPointEvent;
import com.example.BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventHandler {

    private final UserRepository userRepository;

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleUserPointEvent(UserPointEvent event) {
        UserEntity user = userRepository.findById(event.userId())
                .orElseThrow(() -> CustomException.from(UserErrorCode.NOT_FOUND));

        user.deletePoints(event.points());
        userRepository.save(user);
    }
}
