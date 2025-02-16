package com.example.gym.repository;

import com.example.gym.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(Long userId);
    Optional<Payment> findTopByUserIdOrderByPaymentDateDesc(Long userId);
}
