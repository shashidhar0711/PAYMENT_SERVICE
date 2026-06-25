package com.mslearning.PAYMENT_SERVICE.PaymentGateway;

import com.razorpay.RazorpayException;

public interface PaymentGateway {
    String generatePaymentLink(Long orderId, Long amount) throws RazorpayException;
}
