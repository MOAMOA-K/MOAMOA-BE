package com.example.BE.store.service;

import com.example.BE.announcement.domain.dto.AnnouncementResponse;
import com.example.BE.announcement.service.AnnouncementService;
import com.example.BE.feedback.domain.FeedbackEntity;
import com.example.BE.feedback.repository.FeedbackRepository;
import com.example.BE.global.exception.CustomException;
import com.example.BE.global.exception.errorCode.StoreErrorCode;
import com.example.BE.menu.domain.dto.MenuResponse;
import com.example.BE.menu.repository.MenuRepository;
import com.example.BE.store.domain.StoreEntity;
import com.example.BE.store.domain.dto.StoreCreateRequest;
import com.example.BE.store.domain.dto.StoreDetailResponse;
import com.example.BE.store.domain.dto.StoreResponse;
import com.example.BE.store.domain.dto.StoreUpdateRequest;
import com.example.BE.store.repository.StoreRepository;
import java.util.List;
import java.util.Objects;
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
    private final AnnouncementService announcementService;
    private final FeedbackRepository feedbackRepository;

    @Transactional
    public StoreResponse createStore(StoreCreateRequest request, Long userId) {
        StoreEntity newStore = StoreEntity.of(userId, request);

        return StoreResponse.from(storeRepository.save(newStore));
    }

    public StoreDetailResponse getStoreDetail(Long storeId) {
        StoreEntity store = findByIdOrThrow(storeId);
        StoreResponse storeResponse = StoreResponse.from(store);
        List<MenuResponse> menuList = menuRepository.findByStoreId(storeId).stream()
            .map(MenuResponse::from)
            .toList();
        List<AnnouncementResponse> announcementList = announcementService.getAnnouncementsByStoreId(
            storeId);

        return StoreDetailResponse.from(storeResponse, menuList, announcementList);
    }

    public Double getAverageRatings(Long storeId) {
        StoreEntity store = findByIdOrThrow(storeId);
        List<FeedbackEntity> list = feedbackRepository.findAllByStoreId(storeId);
        if (list.isEmpty()) {
            return 0.0;
        }

        int sum = 0;
        for (FeedbackEntity e : list) {
            sum += e.getRating();
        }

        return (double) (sum / list.size());
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
        if (!Objects.equals(store.getUserId(), userId)) {
            throw CustomException.from(StoreErrorCode.FORBIDDEN_STORE_ACCESS);
        }
    }
}