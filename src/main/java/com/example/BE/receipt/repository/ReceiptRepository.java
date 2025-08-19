package com.example.BE.receipt.repository;

import com.example.BE.receipt.domain.ReceiptEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<ReceiptEntity, Long> {
    List<ReceiptEntity> findByUserId(Long userId);
}
