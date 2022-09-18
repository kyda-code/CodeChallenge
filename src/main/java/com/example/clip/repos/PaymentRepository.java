package com.example.clip.repos;

import com.example.clip.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findPaymentsByStatusId(int statusId);
    List<Payment> findPaymentsByUserId(String userId);
}
