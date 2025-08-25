package com.example.BE.receipt.service;

import com.example.BE.global.exception.CustomException;
import com.example.BE.global.exception.errorCode.ReceiptErrorCode;
import com.example.BE.global.exception.errorCode.StoreErrorCode;
import com.example.BE.receipt.domain.ReceiptEntity;
import com.example.BE.receipt.domain.ReceiptEntity.ReceiptStatus;
import com.example.BE.receipt.domain.dto.ReceiptResponse;
import com.example.BE.receipt.domain.dto.ReceiptUpdateRequest;
import com.example.BE.receipt.repository.ReceiptRepository;
import com.example.BE.receipt.service.OcrService.OcrResult;
import com.example.BE.store.domain.StoreEntity;
import com.example.BE.store.repository.StoreRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final StoreRepository storeRepository;
    private final OcrService ocrService;

    @Transactional
    public ReceiptResponse ocrAndCreateReceipt(Long userId, MultipartFile imageFile) {
        try {
            OcrResult ocrResult = ocrService.scanReceipt(imageFile);
            Long storeId = getStoreIdByName(ocrResult.storeName());

            // 영수증 중복 등록 검증
            // ocrResult.datetime, storeId로 비교
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(ocrResult.dateTime(),
                dateTimeFormatter);

            if (receiptRepository.existsByIssuedAtAndStoreId(localDateTime, storeId)) {
                throw CustomException.from(ReceiptErrorCode.ALREADY_EXIST_RECEIPT);
            }

            ReceiptEntity newReceipt = ReceiptEntity.ofWithOcr(userId, storeId, ocrResult);
            return ReceiptResponse.from(receiptRepository.save(newReceipt));
        } catch (IOException e) {
            throw CustomException.from(ReceiptErrorCode.OCR_REQUEST_FAILED);
        }
    }

    @Transactional
    public ReceiptResponse receiptAsDone(Long userId, Long receiptId) {
        ReceiptEntity receipt = findByIdOrThrow(receiptId);
        validateReceiptOwner(userId, receipt);

        receipt.asDone(); // 영수증 상태를 DONE으로 변경
        return ReceiptResponse.from(receipt);
    }

    @Transactional
    public ReceiptResponse updateReceipt(Long userId, Long receiptId,
        ReceiptUpdateRequest request) {
        ReceiptEntity receipt = findByIdOrThrow(receiptId);
        validateReceiptOwner(userId, receipt);

        Long storeId = getStoreIdByName(request.storeName());

        receipt.update(storeId, request.storeName(), request.totalPrice());
        return ReceiptResponse.from(receipt);
    }

    public ReceiptResponse getReceiptById(Long userId, Long receiptId) {
        ReceiptEntity receipt = findByIdOrThrow(receiptId);
        validateReceiptOwner(userId, receipt); // 자신의 영수증만 조회 가능
        return ReceiptResponse.from(receipt);
    }

    public List<ReceiptResponse> getReceiptsByUser(Long userId) {
        return receiptRepository.findByUserId(userId).stream()
            .map(ReceiptResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteReceipt(Long userId, Long receiptId) {
        ReceiptEntity receipt = findByIdOrThrow(receiptId);
        validateReceiptOwner(userId, receipt);

        receiptRepository.deleteById(receiptId);
    }

    private ReceiptEntity findByIdOrThrow(Long receiptId) {
        return receiptRepository.findById(receiptId)
            .orElseThrow(() -> CustomException.from(ReceiptErrorCode.RECEIPT_NOT_FOUND));
    }

    private void validateReceiptOwner(Long userId, ReceiptEntity receipt) {
        if (!Objects.equals(receipt.getUserId(), userId)) {
            throw CustomException.from(ReceiptErrorCode.FORBIDDEN_RECEIPT_ACCESS);
        }
    }

    private Long getStoreIdByName(String storeName) {
        // replaceAll()에서 NotNull 제약 조건 피하기 위함
        if (storeName == null) {
            storeName = "";
        }
        String canonicalName = storeName.replaceAll("\\s+", "");

        StoreEntity store = storeRepository.findByCanonicalName(canonicalName)
            .orElseThrow(() -> CustomException.from(StoreErrorCode.STORE_NOT_FOUND));

        return store.getId();
    }

    public ReceiptEntity validateReceipt(Long receiptId) {
        ReceiptEntity receipt = receiptRepository.findById(receiptId)
            .orElseThrow(() -> CustomException.from(ReceiptErrorCode.RECEIPT_NOT_FOUND));

        if (receipt.getStatus() != ReceiptStatus.AVAILABLE) {
            throw CustomException.from(ReceiptErrorCode.RECEIPT_NOT_AUTHORIZED);
        }
        return receipt;
    }

}
