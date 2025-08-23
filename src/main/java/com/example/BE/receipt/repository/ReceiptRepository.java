package com.example.BE.receipt.repository;

import com.example.BE.receipt.domain.ReceiptEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReceiptRepository extends JpaRepository<ReceiptEntity, Long> {

    List<ReceiptEntity> findByUserId(Long userId);

    boolean existsByIssuedAtAndStoreId(LocalDateTime localDateTime, Long storeId);
}
