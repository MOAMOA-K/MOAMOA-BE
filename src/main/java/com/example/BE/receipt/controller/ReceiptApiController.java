package com.example.BE.receipt.controller;

import com.example.BE.receipt.domain.dto.ReceiptResponse;
import com.example.BE.receipt.domain.dto.ReceiptUpdateRequest;
import com.example.BE.receipt.service.ReceiptService;
import com.example.BE.user.domain.dto.MessageResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/receipt")
public class ReceiptApiController {

    private final ReceiptService receiptService;

    // 1. 영수증 인식/등록
    @PostMapping("/ocr")
    public ResponseEntity<ReceiptResponse> ocrReceipt(
        @AuthenticationPrincipal Long userId,
        @RequestParam("image") MultipartFile imageFile
    ) {
        ReceiptResponse response = receiptService.ocrAndCreateReceipt(userId, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
    }

    // 2. 영수증 완료 처리
    // API도 뚫어놨지만, 피드백 완료 로직에도 완료 로직을 추가해놨습니다.
    @PatchMapping("/done/{receiptId}")
    public ResponseEntity<ReceiptResponse> markDone(
        @AuthenticationPrincipal Long userId,
        @PathVariable Long receiptId
    ) {
        ReceiptResponse response = receiptService.receiptAsDone(userId, receiptId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }

    // 3. 영수증 정보 수정
    @PatchMapping("/{receiptId}")
    public ResponseEntity<ReceiptResponse> updateReceipt(
        @AuthenticationPrincipal Long userId,
        @PathVariable Long receiptId,
        @Valid @RequestBody ReceiptUpdateRequest request
    ) {
        ReceiptResponse response = receiptService.updateReceipt(userId, receiptId, request);
        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }

    // 4. 영수증 단건 조회
    @GetMapping("/{receiptId}")
    public ResponseEntity<ReceiptResponse> getReceipt(
        @AuthenticationPrincipal Long userId,
        @PathVariable Long receiptId
    ) {
        ReceiptResponse response = receiptService.getReceiptById(userId, receiptId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }

    // 5. 영수증 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<List<ReceiptResponse>> getReceiptList(
        @AuthenticationPrincipal Long userId
    ) {
        List<ReceiptResponse> response = receiptService.getReceiptsByUser(userId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }

    // 6. 영수증 삭제
    @DeleteMapping("/{receiptId}")
    public ResponseEntity<MessageResponse> deleteReceipt(
        @AuthenticationPrincipal Long userId,
        @PathVariable Long receiptId
    ) {
        receiptService.deleteReceipt(userId, receiptId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(MessageResponse.from("영수증이 삭제되었습니다."));
    }
}
