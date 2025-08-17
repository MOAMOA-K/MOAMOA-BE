package com.example.BE.receipt.repository;

import com.example.BE.receipt.domain.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<ReceiptEntity, Long> {

}
