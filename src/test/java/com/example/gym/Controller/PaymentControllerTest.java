package com.example.gym.Controller;

import com.example.gym.controller.PaymentController;
import com.example.gym.model.Payment;
import com.example.gym.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPayments_returnsAllPayments() {
        Payment payment1 = new Payment();
        Payment payment2 = new Payment();
        List<Payment> payments = Arrays.asList(payment1, payment2);

        when(paymentService.getAllPayments()).thenReturn(payments);

        ResponseEntity<List<Payment>> response = paymentController.getAllPayments();

        assertEquals(ResponseEntity.ok(payments), response);
    }

    @Test
    void getAllPayments_returnsEmptyListWhenNoPayments() {
        when(paymentService.getAllPayments()).thenReturn(Arrays.asList());

        ResponseEntity<List<Payment>> response = paymentController.getAllPayments();

        assertEquals(ResponseEntity.ok(Arrays.asList()), response);
    }
}
