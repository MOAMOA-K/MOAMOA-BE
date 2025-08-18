package com.example.BE.receipt.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class OcrService {

    private final ObjectMapper objectMapper;
    @Value("${naver.ocr.api-url}")
    private String apiUrl;
    @Value("${naver.ocr.secret-key}")
    private String secretKey;

    public OcrResult scanReceipt(MultipartFile imageFile) throws IOException {

        RestTemplate restTemplate = new RestTemplate();

        // 요청
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("X-OCR-SECRET", secretKey);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        Map<String, Object> imageInfo = new HashMap<>();
        imageInfo.put("format", imageFile.getContentType().split("/")[1]);
        imageInfo.put("name", "receipt");

        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("images", List.of(imageInfo));
        messageMap.put("requestId", UUID.randomUUID().toString());
        messageMap.put("version", "V2");
        messageMap.put("timestamp", System.currentTimeMillis());

        String jsonMessage = objectMapper.writeValueAsString(messageMap);
        body.add("message", jsonMessage);

        ByteArrayResource imageResource = new ByteArrayResource(imageFile.getBytes()) {
            @Override
            public String getFilename() {
                return imageFile.getOriginalFilename();
            }
        };
        body.add("file", imageResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String response = restTemplate.postForObject(apiUrl, requestEntity, String.class);

        // 응답 해석
        JsonNode root = objectMapper.readTree(response);
        JsonNode fields = root.path("images").get(0).path("fields");
        StringBuilder storeNameBuilder = new StringBuilder();
        StringBuilder addressBuilder = new StringBuilder();
        StringBuilder dateTimeBuilder = new StringBuilder();
        StringBuilder totalPriceBuilder = new StringBuilder();

        // 약간 하드코딩 같긴 한데 어쩔 수 없어요.....
        String currentSection = ""; // 현재 어떤 섹션의 텍스트를 읽고 있는지

        for (JsonNode field : fields) {
            String text = field.path("inferText").asText();

            // 섹션의 시작을 감지
            if (text.contains("[매장명]")) {
                currentSection = "STORE_NAME";
                storeNameBuilder.append(text.replace("[매장명]", "").trim()).append(" ");
                continue;
            } else if (text.contains("[주소]")) {
                currentSection = "ADDRESS";
                addressBuilder.append(text.replace("[주소]", "").trim()).append(" ");
                continue;
            } else if (text.contains("[매출일]")) {
                currentSection = "DATETIME";
                dateTimeBuilder.append(text.replace("[매출일]", "").trim()).append(" ");
                continue;
            } else if (text.contains("합계금액")) {
                currentSection = "TOTAL_PRICE";
                totalPriceBuilder.append(text.replace("합계금액", "").trim()).append(" ");
                continue;
            }

            // 섹션의 끝을 감지
            if (text.contains("[사업자번호]")) {
                if (currentSection.equals("STORE_NAME")) {
                    currentSection = "";
                }
            } else if (text.contains("[대표자]")) {
                if (currentSection.equals("ADDRESS")) {
                    currentSection = "";
                }
            } else if (text.contains("상품명")) { // '상품명' 이 포함된 라인을 만나면 날짜 섹션 종료
                if (currentSection.equals("DATETIME")) {
                    currentSection = "";
                }
            } else if (text.contains("신용승인정보")) { // '신용승인정보' 가 포함된 라인을 만나면 합계금액 섹션 종료
                if (currentSection.equals("TOTAL_PRICE")) {
                    currentSection = "";
                }
            }

            switch (currentSection) {
                case "STORE_NAME" -> storeNameBuilder.append(text).append(" ");
                case "ADDRESS" -> addressBuilder.append(text).append(" ");
                case "DATETIME" -> dateTimeBuilder.append(text).append(" ");
                case "TOTAL_PRICE" -> totalPriceBuilder.append(text).append(" ");
            }
        }

        return new OcrResult(
            storeNameBuilder.toString().trim(),
            addressBuilder.toString().trim(),
            dateTimeBuilder.toString().trim(),
            totalPriceBuilder.toString().trim()
        );
    }

    public record OcrResult(String storeName, String address, String dateTime, String totalPrice) {
    }
}