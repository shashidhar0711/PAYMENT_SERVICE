package com.mslearning.PAYMENT_SERVICE.Repository;

import com.mslearning.PAYMENT_SERVICE.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
