package com.example.BE.announcement.repository;

import com.example.BE.announcement.domain.AnnouncementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, Long> {

    List<AnnouncementEntity> findByStoreId(Long storeId);
}