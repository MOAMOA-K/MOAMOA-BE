package com.example.BE.store.service;

import com.example.BE.announcement.repository.AnnouncementRepository;
import com.example.BE.global.exception.CustomException;
import com.example.BE.global.exception.errorCode.AnnouncementErrorCode;
import com.example.BE.global.exception.errorCode.StoreErrorCode;
import com.example.BE.menu.repository.MenuRepository;
import com.example.BE.store.domain.StoreEntity;
import com.example.BE.store.domain.dto.StoreCreateRequest;
import com.example.BE.store.domain.dto.StoreDetailResponse;
import com.example.BE.store.domain.dto.StoreResponse;
import com.example.BE.store.domain.dto.StoreUpdateRequest;
import com.example.BE.store.repository.StoreRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StoreService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final AnnouncementRepository announcementRepository;

    @Transactional
    public StoreResponse createStore(StoreCreateRequest request, Long userId) {
        StoreEntity newStore = StoreEntity.of(userId, request);
        validateStoreOwner(newStore, userId);

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
        List<StoreEntity> stores = storeRepository.findNearbyStoresWithKeyword(lat, lon, radius,
            keyword);
        return stores.stream().map(StoreResponse::from).collect(Collectors.toList());
    }

    @Transactional
    public StoreResponse updateStore(Long storeId, StoreUpdateRequest request, Long userId) {
        StoreEntity store = findByIdOrThrow(storeId);
        validateStoreOwner(store, userId);

        store.update(request);
        return StoreResponse.from(store);
    }

    @Transactional
    public void deleteStore(Long userId, Long storeId) {
        StoreEntity store = findByIdOrThrow(storeId);
        validateStoreOwner(store, userId);

        storeRepository.deleteById(storeId);
    }

    private StoreEntity findByIdOrThrow(Long storeId) {
        return storeRepository.findById(storeId)
            .orElseThrow(() -> CustomException.from(StoreErrorCode.STORE_NOT_FOUND));
    }

    private void validateStoreOwner(StoreEntity store, Long userId) {
        if (!store.getUserId().equals(userId)) {
            throw CustomException.from(StoreErrorCode.FORBIDDEN_STORE_ACCESS);
        }
    }
}