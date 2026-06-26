package com.mslearning.PAYMENT_SERVICE.Dto;

import lombok.Data;

@Data
public class PaymentSuccessRequestDto {
    private Long ticketId;
    private String gatewayPaymentId;
}
