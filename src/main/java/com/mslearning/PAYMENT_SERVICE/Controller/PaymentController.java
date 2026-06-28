package com.mslearning.PAYMENT_SERVICE.Controller;

import com.mslearning.PAYMENT_SERVICE.Dto.CreatePaymentResponseDto;
import com.mslearning.PAYMENT_SERVICE.Dto.PaymentRequest;
import com.mslearning.PAYMENT_SERVICE.Exception.PaymentNotFoundException;
import com.mslearning.PAYMENT_SERVICE.Model.Payment;
import com.mslearning.PAYMENT_SERVICE.Services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/webhook")
    public ResponseEntity<Void> webhook(
            @RequestBody String payload,
            @RequestHeader("X-Razorpay-Signature") String razorpaySignature)
            throws Exception {

        paymentService.processWebhook(payload, razorpaySignature);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> getPayment(
            @PathVariable Long paymentId) throws PaymentNotFoundException {

        return ResponseEntity.ok(
                paymentService.getPaymentById(paymentId));
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<Payment> getPaymentByTicket(
            @PathVariable Long ticketId) throws PaymentNotFoundException {

        return ResponseEntity.ok(
                paymentService.getPaymentByTicketId(ticketId));
    }
}
