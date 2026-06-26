package com.mslearning.PAYMENT_SERVICE.Services;

import com.mslearning.PAYMENT_SERVICE.Dto.CreatePaymentResponseDto;
import com.mslearning.PAYMENT_SERVICE.Dto.PaymentSuccessRequestDto;
import com.mslearning.PAYMENT_SERVICE.Dto.TicketDetailsDto;
import com.mslearning.PAYMENT_SERVICE.Model.Payment;
import com.mslearning.PAYMENT_SERVICE.Model.PaymentStatus;
import com.mslearning.PAYMENT_SERVICE.PaymentGateway.PaymentGateway;
import com.mslearning.PAYMENT_SERVICE.Repository.PaymentRepository;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {
    private PaymentGateway paymentGateway;
    private RestTemplate restTemplate;
    private PaymentRepository paymentRepository;

    @Value("${razorpay.webhook.secret}")
    private String webhookSecret;

    public PaymentService(PaymentGateway paymentGateway,
                          RestTemplate restTemplate,
                          PaymentRepository paymentRepository) {
        this.paymentGateway = paymentGateway;
        this.restTemplate = restTemplate;
        this.paymentRepository = paymentRepository;
    }

    public CreatePaymentResponseDto initiatePayment(Long ticketId) throws RazorpayException {

        // call the service and get the amount
        TicketDetailsDto ticketResponse = this.restTemplate.getForObject(
                "http://localhost:8081/tickets/internal/" + ticketId,
                TicketDetailsDto.class
        );

        Long totalAmount = ticketResponse.getTotalAmount();
        PaymentLink paymentLink = this.paymentGateway.generatePaymentLink(ticketId,totalAmount);

        Payment payment = new Payment();

        payment.setTicketId(ticketId);
        payment.setAmount(totalAmount);

        String short_url = paymentLink.get("short_url").toString();
        payment.setPaymentLink(short_url);
        payment.setPaymentStatus(PaymentStatus.PENDING);

        Payment savedPayment = this.paymentRepository.save(payment);


        CreatePaymentResponseDto response = new CreatePaymentResponseDto();
        response.setPaymentId(savedPayment.getId());
        response.setPaymentLink(savedPayment.getPaymentLink());

        return response;
    }

    public void processWebhook(String payload, String razorpaySignature) throws RazorpayException {
        // Verify webhook signature
        boolean valid = Utils.verifyWebhookSignature(
                payload,
                razorpaySignature,
                webhookSecret
        );

        if (!valid) {
            throw new RuntimeException("Invalid webhook signature");
        }

        JSONObject json = new JSONObject(payload);
        String event = json.getString("event");
        if (!event.equals("payment.captured")) {
            return;
        }

        JSONObject payment =
                json.getJSONObject("payload")
                        .getJSONObject("payment")
                        .getJSONObject("entity");

        JSONObject paymentLink =
                json.getJSONObject("payload")
                        .getJSONObject("payment_link")
                        .getJSONObject("entity");

        String razorpayPaymentId = payment.getString("id");

        Long ticketId = Long.parseLong(
                paymentLink.getString("reference_id")
        );

        Payment savedPayment = paymentRepository.findByTicketId(ticketId)
                        .orElseThrow();

        savedPayment.setPaymentStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(savedPayment);

        PaymentSuccessRequestDto request = new PaymentSuccessRequestDto();

        request.setTicketId(ticketId);
        request.setGatewayPaymentId(razorpayPaymentId);

        restTemplate.postForObject(
                "http://localhost:8081/tickets/internal/payment-success",
                request,
                Void.class
        );
    }
}
