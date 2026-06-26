package com.mslearning.PAYMENT_SERVICE.Controller;

import com.mslearning.PAYMENT_SERVICE.Dto.CreatePaymentResponseDto;
import com.mslearning.PAYMENT_SERVICE.Dto.PaymentRequest;
import com.mslearning.PAYMENT_SERVICE.Services.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/")
    public CreatePaymentResponseDto IntiatePayment(@RequestBody PaymentRequest request) {
        try {
            return this.paymentService.initiatePayment(request.getTicketId());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
