package com.example.gym.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Admin")
public class Admin {
    @Id
    private String adminId;

    @Column(name = "Name")
    private String adminName;

    @Column(name = "Email")
    private String adminEmail;

    @Column(name = "Password")
    private String adminPassword;

    @Column(name = "Mobile")
    private String adminMobile;
}
