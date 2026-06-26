package com.mslearning.PAYMENT_SERVICE.PaymentGateway;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;

public interface PaymentGateway {
    PaymentLink generatePaymentLink(Long orderId, Long amount) throws RazorpayException;
}
