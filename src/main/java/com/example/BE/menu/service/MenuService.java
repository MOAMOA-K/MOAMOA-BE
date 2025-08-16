package com.example.BE.menu.service;

import com.example.BE.global.exception.CustomException;
import com.example.BE.global.exception.errorCode.MenuErrorCode;
import com.example.BE.global.exception.errorCode.StoreErrorCode;
import com.example.BE.menu.domain.MenuEntity;
import com.example.BE.menu.domain.dto.MenuCreateRequest;
import com.example.BE.menu.domain.dto.MenuResponse;
import com.example.BE.menu.domain.dto.MenuUpdateRequest;
import com.example.BE.menu.repository.MenuRepository;
import com.example.BE.store.domain.StoreEntity;
import com.example.BE.store.repository.StoreRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public MenuResponse createMenu(Long userId, MenuCreateRequest request) {
        StoreEntity store = findStoreByIdOrThrow(request.storeId());

        // 가게 주인만 메뉴 생성/수정/삭제할 수 있도록 함.
        validateStoreOwner(userId, store);

        MenuEntity newMenu = MenuEntity.from(request);

        return MenuResponse.from(menuRepository.save(newMenu));
    }

    public MenuResponse getMenuById(Long menuId) {
        MenuEntity menu = findByIdOrThrow(menuId);
        return MenuResponse.from(menu);
    }

    public List<MenuResponse> getMenusByStoreId(Long storeId) {
        List<MenuEntity> menus = menuRepository.findByStoreId(storeId);
        return menus.stream()
            .map(MenuResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public MenuResponse updateMenu(Long userId, Long menuId, MenuUpdateRequest request) {
        MenuEntity menu = findByIdOrThrow(menuId);
        StoreEntity store = findStoreByIdOrThrow(menu.getStoreId());
        validateStoreOwner(userId, store);

        menu.update(request);
        return MenuResponse.from(menu);
    }

    @Transactional
    public void deleteMenu(Long userId, Long menuId) {
        MenuEntity menu = findByIdOrThrow(menuId);
        StoreEntity store = findStoreByIdOrThrow(menu.getStoreId());
        validateStoreOwner(userId, store);

        menuRepository.deleteById(menuId);
    }

    private MenuEntity findByIdOrThrow(Long menuId) {
        return menuRepository.findById(menuId)
            .orElseThrow(() -> CustomException.from(MenuErrorCode.MENU_NOT_FOUND));
    }

    private StoreEntity findStoreByIdOrThrow(Long storeId) {
        return storeRepository.findById(storeId)
            .orElseThrow(() -> CustomException.from(StoreErrorCode.STORE_NOT_FOUND));
    }

    // 요청한 사용자가 메뉴를 생성/수정/삭제할 수 있는 권한을 가졌는지 확인
    private void validateStoreOwner(Long userId, StoreEntity store) {
        if (!store.getUserId().equals(userId)) {
            throw CustomException.from(MenuErrorCode.FORBIDDEN_MENU_ACCESS);
        }
    }
}