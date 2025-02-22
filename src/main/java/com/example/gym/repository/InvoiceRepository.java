package com.example.gym.repository;

import com.example.gym.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice , Long> {
    Optional<Invoice> findByUserId(Long userId);
}
