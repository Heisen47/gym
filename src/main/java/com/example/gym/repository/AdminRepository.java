package com.example.gym.repository;

import com.example.gym.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin , String> {
    Admin findByAdminEmail(String adminEmail);
    Admin findByAdminEmailAndAdminPassword(String adminEmail, String adminPassword);
}
