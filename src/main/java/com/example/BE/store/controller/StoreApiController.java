package com.example.BE.store.controller;

import com.example.BE.store.domain.dto.StoreCreateRequest;
import com.example.BE.store.domain.dto.StoreDetailResponse;
import com.example.BE.store.domain.dto.StoreResponse;
import com.example.BE.store.domain.dto.StoreUpdateRequest;
import com.example.BE.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreApiController {

    private final StoreService storeService;

    // 가게 생성
    @PostMapping
    public ResponseEntity<StoreResponse> createStore(
        @AuthenticationPrincipal Long userId,
        @Valid @RequestBody StoreCreateRequest request
    ) {
        StoreResponse response = storeService.createStore(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
    }

    // 가게 상세 정보 조회
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreDetailResponse> getStoreDetail(
        @PathVariable Long storeId
    ) {
        StoreDetailResponse response = storeService.getStoreDetail(storeId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }

    // 가게별 평균 평점 조회
    @GetMapping("/{storeId}/ratings")
    public ResponseEntity<?> getRatings(
        @PathVariable Long storeId
    ){
        Double avgRating = storeService.getAverageRatings(storeId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(avgRating);
    }

    // 인접 가게 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<List<StoreResponse>> getStoreList(
        @RequestParam Double latitude,
        @RequestParam Double longitude,
        @RequestParam(required = false, defaultValue = "") String keyword
    ) {
        List<StoreResponse> response = storeService.searchStores(latitude, longitude, keyword);
        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }

    // 가게 정보 수정
    @PatchMapping("/{storeId}")
    public ResponseEntity<StoreResponse> updateStore(
        @AuthenticationPrincipal Long userId,
        @PathVariable Long storeId,
        @Valid @RequestBody StoreUpdateRequest request
    ) {
        StoreResponse response = storeService.updateStore(storeId, request, userId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }

    // 가게 삭제
    // ADMIN 전용 API로 변경 예정
    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> deleteStore(
        @AuthenticationPrincipal Long userId,
        @PathVariable Long storeId
    ) {
        storeService.deleteStore(userId, storeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}