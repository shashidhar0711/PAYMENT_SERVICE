package com.mslearning.PAYMENT_SERVICE.PaymentGateway;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;

public class StripePaymentServiceImpl implements PaymentGateway{
    @Override
    public PaymentLink generatePaymentLink(Long orderId, Long amount) throws RazorpayException {
        return null;
    }
}
