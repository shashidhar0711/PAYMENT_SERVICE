package com.mslearning.PAYMENT_SERVICE.Services;

import com.mslearning.PAYMENT_SERVICE.PaymentGateway.PaymentGateway;
import com.razorpay.RazorpayException;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private PaymentGateway paymentGateway;

    public PaymentService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public String initiatePayment(Long orderId, Long amount) throws RazorpayException {

        // call the product service/ order service..
        // get price..
        return this.paymentGateway.generatePaymentLink(orderId,amount);
    }

}
