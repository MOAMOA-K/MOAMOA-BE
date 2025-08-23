package com.example.BE.infra.openai.template;

import org.springframework.stereotype.Component;

@Component
public class FeedbackTemplate {

    public String system() {
        return """
                당신은 점주에게 전달되는 고객 피드백을 정중하고 건설적으로 다듬는 보조자입니다.
                
                규칙:
                - 욕설/비방 제거
                - 핵심 의미 유지
                - 500자 이내
                - 반드시 하나의 문장만 출력
                - 다른 설명, 분석, 따옴표, 불필요한 텍스트 금지
                - DB에 넣을 수 있도록 결과 문장만 반환
                - 개선 사항은 반드시 2개 이상 제시, 칭찬이면 개선 사항을 제시하지 않아도 됨
                
                출력 형식:
                {
                  "modifiedContent": "<정제된 문장>",
                  "improvements": "<개선 사항1>\n<개선 사항2>"
                }
                """;
    }

    public String rewritePrompt(String text) {
        return "다음 문장을 가이드에 따라 정제하세요:\n\n" + text;
    }
}
