package com.example.BE.menu.controller;

import com.example.BE.menu.domain.dto.MenuCreateRequest;
import com.example.BE.menu.domain.dto.MenuResponse;
import com.example.BE.menu.domain.dto.MenuUpdateRequest;
import com.example.BE.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuApiController {

    private final MenuService menuService;

    // 메뉴 생성
    @PostMapping
    public ResponseEntity<MenuResponse> createMenu(
        @AuthenticationPrincipal Long userId,
        @RequestBody MenuCreateRequest request
    ) {
        MenuResponse response = menuService.createMenu(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 메뉴 단일 조회: 거의 사용 안 할 듯
    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponse> getMenu(
        @PathVariable Long menuId
    ) {
        MenuResponse response = menuService.getMenuById(menuId);
        return ResponseEntity.ok(response);
    }

    // 특정 가게 메뉴 리스트 조회
    @GetMapping("/list/{storeId}")
    public ResponseEntity<List<MenuResponse>> getMenuList(
        @PathVariable Long storeId
    ) {
        List<MenuResponse> response = menuService.getMenusByStoreId(storeId);
        return ResponseEntity.ok(response);
    }

    // 메뉴 정보 수정
    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuResponse> updateMenu(
        @AuthenticationPrincipal Long userId,
        @PathVariable Long menuId,
        @RequestBody MenuUpdateRequest request
    ) {
        MenuResponse response = menuService.updateMenu(userId, menuId, request);
        return ResponseEntity.ok(response);
    }

    // 메뉴 삭제
    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(
        @AuthenticationPrincipal Long userId,
        @PathVariable Long menuId
    ) {
        menuService.deleteMenu(userId, menuId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}