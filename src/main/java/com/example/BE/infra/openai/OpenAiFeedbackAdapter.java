package com.example.BE.infra.openai;

import com.example.BE.infra.openai.template.FeedbackTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenAiFeedbackAdapter implements AiRewritePort {

    private final ChatClient chatClient;
    private final FeedbackTemplate prompts;

    @Override
    public String rewrite(String original) {
        String system = prompts.system();
        String prompt = prompts.rewritePrompt(original);

        return chatClient
                .prompt()
                .system(system)
                .user(prompt)
                .call()
                .content();
    }
}
