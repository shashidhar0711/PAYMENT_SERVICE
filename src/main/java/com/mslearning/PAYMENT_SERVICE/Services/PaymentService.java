package com.mslearning.PAYMENT_SERVICE.Services;

import com.mslearning.PAYMENT_SERVICE.Dto.CreatePaymentResponseDto;
import com.mslearning.PAYMENT_SERVICE.Dto.TicketDetailsDto;
import com.mslearning.PAYMENT_SERVICE.Model.Payment;
import com.mslearning.PAYMENT_SERVICE.Model.PaymentStatus;
import com.mslearning.PAYMENT_SERVICE.PaymentGateway.PaymentGateway;
import com.mslearning.PAYMENT_SERVICE.Repository.PaymentRepository;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {
    private PaymentGateway paymentGateway;
    private RestTemplate restTemplate;
    private PaymentRepository paymentRepository;

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

}
