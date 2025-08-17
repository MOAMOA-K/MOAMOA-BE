package com.example.BE.global.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.ai.openai")
public class OpenAiProperties {

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
