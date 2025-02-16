package com.example.gym.dto;

import lombok.Data;

@Data
public class InvoiceRequest {
    private String invoiceAmount;
    private String invoicedBy;
    private Long userId;
}
