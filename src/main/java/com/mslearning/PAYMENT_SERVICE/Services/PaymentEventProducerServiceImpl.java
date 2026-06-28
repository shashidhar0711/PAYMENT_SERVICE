package com.mslearning.PAYMENT_SERVICE.Services;

import com.mslearning.PAYMENT_SERVICE.Dto.PaymentSuccessEvent;
import com.mslearning.PAYMENT_SERVICE.Model.Payment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventProducerServiceImpl {
    private static final String TOPIC="payment_success_topic";

    private final KafkaTemplate<String, PaymentSuccessEvent> kafkaTemplate;

    public PaymentEventProducerServiceImpl(KafkaTemplate<String, PaymentSuccessEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(PaymentSuccessEvent event) {
        this.kafkaTemplate.send(
                TOPIC,
                String.valueOf(event.getTicketId()),
                event
        );
    }


}


