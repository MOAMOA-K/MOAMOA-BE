package com.example.BE.infra.openai.dto;

public record AiFeedbackResponse(
        String modifiedContent,
        String improvements
) {

}
