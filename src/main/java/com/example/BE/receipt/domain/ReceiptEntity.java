package com.example.BE.receipt.domain;

import com.example.BE.receipt.service.OcrService.OcrResult;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "receipts")
@Getter
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "store_name", length = 128)
    private String storeName;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private ReceiptStatus status;

    @Column(name = "total_price")
    private Long totalPrice;

    @Builder
    public ReceiptEntity(Long userId, Long storeId, String storeName,
            LocalDateTime issuedAt, ReceiptStatus status, Long totalPrice) {
        this.userId = userId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.issuedAt = issuedAt;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    // ofWithOcr
    public static ReceiptEntity ofWithOcr(Long userId, Long storeId, OcrResult ocrResult){
        String totalPriceString = ocrResult.totalPrice().replaceAll("[^0-9]", "");
        Long totalPrice = Long.parseLong(totalPriceString);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(ocrResult.dateTime(), dateTimeFormatter);

        return new ReceiptEntity(
            userId,
            storeId,
            ocrResult.storeName(),
            localDateTime,
            ReceiptStatus.AVAILABLE,
            totalPrice
        );
    }

    // asDone
    public void asDone(){
        this.status = ReceiptStatus.DONE;
    }

    // update
    public void update(Long storeId, String storeName, Long totalPrice){
        this.storeId=storeId;
        this.storeName=storeName;
        this.totalPrice=totalPrice;
    }


    public enum ReceiptStatus{
        AVAILABLE,
        DONE
    }
}
