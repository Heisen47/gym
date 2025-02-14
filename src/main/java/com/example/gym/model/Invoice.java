package com.example.gym.model;

import com.example.gym.util.CustomIdGenerator;
import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Entity
@Data
@Table(name = "Invoice")
public class Invoice {
    @Id
    private Long InvoiceId;

    @Column(nullable = false)
    private ZonedDateTime InvoiceDate;

    @Column(nullable = false)
    private String InvoiceAmount;

    @Column(nullable = false)
    private String PaymentMethod;

    @Column(name = "expiry_date", nullable = false)
    private ZonedDateTime validity;

    @OneToOne
    private Payment payment;

    @PrePersist
    public void prePersist() {
        if (this.InvoiceId == null) {
            this.InvoiceId = CustomIdGenerator.generateId();
        }
    }
}
