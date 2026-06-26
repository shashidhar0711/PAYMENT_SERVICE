package com.mslearning.PAYMENT_SERVICE.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Entity
public class Payment extends BaseModel {
    private Long ticketId;
    private Long amount;
    private String paymentLink;

    @Enumerated
    private PaymentStatus paymentStatus;
}
