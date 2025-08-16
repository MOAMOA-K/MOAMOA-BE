package com.example.BE.store.service;

import com.example.BE.global.exception.CustomException;
import com.example.BE.global.exception.errorCode.StoreErrorCode;
import com.example.BE.store.domain.StoreEntity;
import com.example.BE.store.domain.dto.StoreCreateRequest;
import com.example.BE.store.domain.dto.StoreDetailResponse;
import com.example.BE.store.domain.dto.StoreResponse;
import com.example.BE.store.domain.dto.StoreUpdateRequest;
import com.example.BE.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StoreService {

    private final StoreRepository storeRepository;
    //private final MenuRepository menuRepository; // 상세 조회를 위해 주입
    //private final StoreAnnouncementRepository announcementRepository; // 상세 조회를 위해 주입

    @Transactional
    public StoreResponse createStore(StoreCreateRequest request, Long userId) {
        StoreEntity newStore = StoreEntity.builder()
            .userId(userId)
            .name(request.name())
            .canonicalName(request.name().replaceAll(" ", ""))
            .address(request.address())
            .latitude(request.latitude())
            .longitude(request.longitude())
            .description(request.description())
            .category(request.category())
            .imageUrl(request.imageUrl())
            .openingTime(request.openingTime())
            .build();
        return StoreResponse.from(storeRepository.save(newStore));
    }

    public StoreDetailResponse getStoreDetail(Long storeId) {
        StoreEntity store = findByIdOrThrow(storeId);
        // 메뉴, 개선사항 목록을 조회하고 조합: 아직 구현 전
        // ...
        // return new StoreDetailResponse();
        return null;
    }

    public List<StoreResponse> searchStores(double lat, double lon, String keyword) {
        final int radius = 2000; // 2km 반경으로 고정
        List<StoreEntity> stores = storeRepository.findNearbyStoresWithKeyword(lat, lon, radius, keyword);
        return stores.stream().map(StoreResponse::from).collect(Collectors.toList());
    }

    @Transactional
    public StoreResponse updateStore(Long storeId, StoreUpdateRequest request, Long userId) {
        StoreEntity store = findByIdOrThrow(storeId);
        // 가게 주인 본인인지 확인
//        log.info("storeId: {}", storeId);
//        log.info("store userId: {}", store.getUserId());
//        log.info("request userId: {}", userId);

        if (!store.getUserId().equals(userId)) {
            throw CustomException.from(StoreErrorCode.FORBIDDEN_STORE_ACCESS);
        }
        store.update(request);
        return StoreResponse.from(store);
    }

    @Transactional
    public void deleteStore(Long storeId) {
        findByIdOrThrow(storeId);
        storeRepository.deleteById(storeId);
    }

    private StoreEntity findByIdOrThrow(Long storeId) {
        return storeRepository.findById(storeId)
            .orElseThrow(() -> CustomException.from(StoreErrorCode.STORE_NOT_FOUND));
    }
}