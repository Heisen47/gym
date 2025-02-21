package com.example.gym.exception;

public class InvalidInvoiceDataException extends RuntimeException {
    public InvalidInvoiceDataException(String message) {
        super(message);
    }
}
