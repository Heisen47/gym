package com.example.gym.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidInvoiceDataException extends RuntimeException {
    public InvalidInvoiceDataException(String message) {
        super(message);
    }
}
