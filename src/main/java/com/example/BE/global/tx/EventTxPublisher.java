package com.example.BE.global.tx;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EventTxPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publish(Object event) {
        eventPublisher.publishEvent(event);
    }
}
