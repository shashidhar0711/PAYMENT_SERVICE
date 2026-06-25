package com.mslearning.PAYMENT_SERVICE.Dto;

import lombok.Data;

@Data
public class    PaymentRequest {
    private Long orderId;
    private Long amount;
}
