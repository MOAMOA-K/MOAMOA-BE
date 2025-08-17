package com.example.BE.global.config;

import com.example.BE.global.properties.OpenAiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OpenAiConfig {

    private final OpenAiProperties props;

    @Bean
    public OpenAiApi openAiApi() {
        return OpenAiApi.builder()
                .apiKey(props.getApiKey())
                .baseUrl(props.getChat().getBaseUrl())
                .completionsPath(props.getChat().getCompletionsPath())
                .build();
    }

    @Bean
    public OpenAiChatModel openAiChatModel(OpenAiApi api) {
        OpenAiChatOptions opts = OpenAiChatOptions.builder()
                .model(props.getChat().getOptions().getModel())
                .temperature(props.getChat().getOptions().getTemperature())
                .maxTokens(props.getChat().getOptions().getMaxTokens())
                .build();

        return OpenAiChatModel.builder()
                .openAiApi(api)
                .defaultOptions(opts)
                .build();
    }

    @Bean
    public ChatClient chatClient(OpenAiChatModel model) {
        return ChatClient.create(model);
    }

}
