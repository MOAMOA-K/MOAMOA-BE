package com.example.BE.receipt.service;

import com.example.BE.global.exception.CustomException;
import com.example.BE.global.exception.errorCode.ReceiptErrorCode;
import com.example.BE.receipt.domain.ReceiptEntity;
import com.example.BE.receipt.domain.ReceiptStatus;
import com.example.BE.receipt.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    public ReceiptEntity validateReceipt(Long receiptId) {
        ReceiptEntity receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> CustomException.from(ReceiptErrorCode.RECEIPT_NOT_FOUND));

        if (receipt.getStatus() != ReceiptStatus.AVAILABLE) {
            throw CustomException.from(ReceiptErrorCode.RECEIPT_NOT_AUTHORIZED);
        }
        return receipt;
    }

}
