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

    @Column
    private String InvoicedBy;

    @OneToOne
    private Payment payment;

    @ManyToOne
    private User user;

    @PrePersist
    public void prePersist() {
        if (this.InvoiceId == null) {
            this.InvoiceId = CustomIdGenerator.generateId();
        }
    }
}
