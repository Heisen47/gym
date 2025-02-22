package com.example.gym.controller;

import com.example.gym.dto.InvoiceRequest;
import com.example.gym.model.Invoice;
import com.example.gym.service.InvoiceService;
import com.example.gym.service.PaymentService;
import com.example.gym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/admin")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final UserService userService;
    private final PaymentService paymentService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, UserService userService , PaymentService paymentService) {
        this.invoiceService = invoiceService;
        this.userService = userService;
        this.paymentService = paymentService;
    }

    @PostMapping("/invoice")
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceRequest request) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(ZonedDateTime.now());
        invoice.setInvoiceAmount(request.getInvoiceAmount());
        invoice.setInvoicedBy(request.getInvoicedBy());

        userService.getSingleUser(request.getUserId()).ifPresent(invoice::setUser);
        paymentService.getLatestPaymentForUser(request.getUserId()).ifPresent(invoice::setPayment);

        Invoice savedInvoice = invoiceService.saveInvoice(invoice);
        return ResponseEntity.ok(savedInvoice);
    }

}
