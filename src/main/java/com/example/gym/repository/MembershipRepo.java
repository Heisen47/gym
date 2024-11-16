package com.example.gym.repository;

import com.example.gym.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembershipRepo extends JpaRepository<Membership , Long> {
    Optional<Membership> findByUserId(Long userId);
}
