package com.example.BE.global.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@lombok.ToString(exclude = "apiKey")
@Validated
@Component
@ConfigurationProperties(prefix = "spring.ai.openai")
public class OpenAiProperties {

    @NotBlank
    private String apiKey;

    private Chat chat = new Chat();

    @Data
    public static class Chat {

        private String baseUrl;
        private Options options = new Options();
        private String completionsPath;
    }

    @Data
    public static class Options {

        private String model;
        private Double temperature;
        private Integer maxTokens;
    }
}
