package com.example.gym.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(name = "Name")
    private String adminName;

    @Column(name = "Email")
    private String adminEmail;

    @Column(name = "Password")
    private String adminPassword;

    @Column(name = "Mobile")
    private String adminMobile;
}
