package com.example.BE.feedback.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.BE.global.properties.OpenAiProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;

//
//class FeedbackServiceTest {
//
//    @Autowired
//    public OpenAiProperties openAiProperties;
//
//    public OpenAiChatModel openAiChatModel;
//
//
//
//    @BeforeEach
//    void setUp(){
//        String API_KEY = "my-api-key";
//        String BASE_URL = "https://generativelanguage.googleapis.com/v1beta/openai/";
//        String COMPLETIONS_PATH = "/chat/completions";
//        String MODEL = "gemini-2.0-flash-lite";
//        Double TEMPERATURE = 0.0;
//
//        OpenAiApi openAiApi = OpenAiApi.builder()
//                .apiKey(API_KEY)
//                .baseUrl(BASE_URL)
//                .completionsPath(COMPLETIONS_PATH)
//                .build();
//
//        OpenAiChatOptions chatOptions = new OpenAiChatOptions().builder()
//                .model(MODEL)
//                .temperature(TEMPERATURE)
//                .build();
//
//        openAiChatModel = OpenAiChatModel.builder()
//                .openAiApi(openAiApi)
//                .defaultOptions(chatOptions)
//                .build();
//
//    }
//
//    @Test
//    void testGetModifiedContent() {
//        // Given
//        String content = "음식이 맛있는데 전반적으로 짠 느낌이 많아요. 쩝 아쉽네요";
//        String expectedModifiedContent = "";
//
//
//        // When
//        String actualModifiedContent = openAiChatModel
//                .call(new Prompt("다음 문장을 좀 더 부드럽게 수정해 주세요: " + content))
//                .getResult()
//                .getOutput()
//                .getText();
//
//
//        // Then
//        assertNotNull(actualModifiedContent);
//        System.out.println("AI가 수정한 피드백 결과: " + actualModifiedContent);
//    }
//}