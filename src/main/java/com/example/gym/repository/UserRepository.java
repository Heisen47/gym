package com.example.gym.repository;

import com.example.gym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber (String phoneNumber);
    Optional<User> findByEmail (String Email);
}
