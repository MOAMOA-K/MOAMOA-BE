package com.example.BE.menu.repository;

import com.example.BE.menu.domain.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

    List<MenuEntity> findByStoreId(Long storeId);
}