package com.example.gym.model;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "PaymentData")
public class Payment {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String paymentDate;
    private String paymentAmount;
    private String paymentMethod;

    @Column(name = "Days_of_membership")
    private String validity;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;
}
