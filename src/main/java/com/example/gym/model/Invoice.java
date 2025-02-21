package com.example.gym.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Data
@Table(name = "Invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column
    private ZonedDateTime rowversion;

    @PrePersist
    public void prePersist() {
        this.rowversion = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
    }
}
