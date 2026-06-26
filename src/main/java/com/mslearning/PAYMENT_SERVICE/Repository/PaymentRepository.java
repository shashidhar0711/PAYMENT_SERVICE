package com.mslearning.PAYMENT_SERVICE.Repository;

import com.mslearning.PAYMENT_SERVICE.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByTicketId(Long ticketId);
}
