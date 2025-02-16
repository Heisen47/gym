package com.example.gym.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.ZoneId;
import java.time.ZonedDateTime;


@Entity
@Data
@Table(name = "PaymentData")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Payment_ID")
    private Long PaymentId;

    @Column(nullable = false)
    private ZonedDateTime paymentDate;

    @Column(nullable = false)
    private String paymentAmount;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(name = "expiry_date", nullable = false)
    private ZonedDateTime validity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "row_version", columnDefinition = "TIMESTAMP")
    private ZonedDateTime rowVersion;

    // Auto-set timestamps
    @PrePersist
    public void setInitialTimestamps() {
        ZoneId istZone = ZoneId.of("Asia/Kolkata");
        this.paymentDate = ZonedDateTime.now(istZone);
        this.rowVersion = ZonedDateTime.now(istZone);
    }

    @PreUpdate
    public void updateRowVersion() {
        this.rowVersion = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
    }
}
