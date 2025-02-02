package com.example.gym.controller;

import com.example.gym.model.Payment;
import com.example.gym.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @GetMapping("/payments")
    public ResponseEntity<List<Payment>> getAllPayments(){
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }



}
