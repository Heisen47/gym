package com.example.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminLoginRepo extends JpaRepository<com.example.gym.model.AdminLogin, Long> {
    com.example.gym.model.AdminLogin findByEmail(String email);
}
