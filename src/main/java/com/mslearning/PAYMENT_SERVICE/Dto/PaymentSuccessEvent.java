package com.mslearning.PAYMENT_SERVICE.Dto;

import lombok.Data;

@Data
public class PaymentSuccessEvent {
    private int ticketId;
    private String razorpayPaymentId;
    private Long amount;
}
