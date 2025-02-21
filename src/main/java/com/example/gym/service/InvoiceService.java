package com.example.gym.service;

import com.example.gym.exception.InvalidInvoiceDataException;
import com.example.gym.exception.UserNotFoundException;
import com.example.gym.model.Invoice;
import com.example.gym.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice saveInvoice(Invoice invoice) {
        if (invoice.getInvoiceAmount() == null ) {
            throw new InvalidInvoiceDataException("Invoice amount and payment method are required.");
        }
        if (invoice.getUser() == null) {
            throw new UserNotFoundException("User not found for the given invoice.");
        }
        return invoiceRepository.save(invoice);
    }
}
