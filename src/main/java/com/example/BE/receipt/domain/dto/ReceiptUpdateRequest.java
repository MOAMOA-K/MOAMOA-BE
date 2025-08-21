package com.example.BE.receipt.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReceiptUpdateRequest(
    @NotBlank(message = "가게 이름은 필수입니다.")
    String storeName,

    // datatime 필드 추가 필요

    @NotNull(message = "총 금액은 필수입니다.")
    Long totalPrice
) {

}
