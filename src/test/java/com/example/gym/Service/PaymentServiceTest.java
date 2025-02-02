package com.example.gym.Service;

import com.example.gym.model.Payment;
import com.example.gym.repository.PaymentRepository;
import com.example.gym.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPayments_returnsAllPayments() {
        Payment payment1 = new Payment();
        Payment payment2 = new Payment();
        List<Payment> payments = Arrays.asList(payment1, payment2);

        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(2, result.size());
        assertEquals(payment1, result.get(0));
        assertEquals(payment2, result.get(1));
    }

    @Test
    void getAllPayments_returnsEmptyListWhenNoPayments() {
        when(paymentRepository.findAll()).thenReturn(Arrays.asList());

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(0, result.size());
    }
}
