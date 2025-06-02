package com.aesopwow.subsubclipclop.domain.payment.repository;

import com.aesopwow.subsubclipclop.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
