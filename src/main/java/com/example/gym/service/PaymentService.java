package com.example.gym.service;

import com.example.gym.model.Payment;
import com.example.gym.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

    public Payment updatePayment(Long userId, Payment payment) {
        List<Payment> payments = paymentRepository.findByUserId(userId);
        for (Payment p : payments) {
            if (p.getPaymentId().equals(payment.getPaymentId())) {
                return paymentRepository.save(payment);
            }
        }
        return null;
    }
}
