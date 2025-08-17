package com.example.BE.infra.openai.template;

import org.springframework.stereotype.Component;

@Component
public class FeedbackTemplate {

    public String system() {
        return """
                당신은 점주에게 전달되는 고객 피드백을 정중하고 건설적으로 다듬는 보조자입니다.
                - 욕설/비방 제거
                - 핵심 유지
                - 500자 이내
                """;
    }

    public String rewritePrompt(String text) {
        return "다음 문장을 가이드에 따라 정제하세요:\n\n" + text;
    }
}
