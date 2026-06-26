package com.mslearning.PAYMENT_SERVICE.Dto;

import lombok.Data;

@Data
public class CreatePaymentResponseDto {
    private Long paymentId;
    private String paymentLink;
}