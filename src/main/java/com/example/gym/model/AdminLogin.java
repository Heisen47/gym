package com.example.gym.model;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "Admin")
public class AdminLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;

}
