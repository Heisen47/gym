package com.example.gym.service;

import com.example.gym.dto.UserCreateDTO;
import com.example.gym.dto.UserDTO;
import com.example.gym.exception.EmailAlreadyExistsException;
import com.example.gym.exception.ResourceNotFoundException;
import com.example.gym.model.AdminLogin;
import com.example.gym.repository.MembershipRepo;
import com.example.gym.repository.AdminLoginRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private AdminLoginRepo adminLoginRepo;

    public boolean verifyUser(String email, String password) {
        AdminLogin admin = adminLoginRepo.findByEmail(email);
        if (admin != null && admin.getPassword().equals(password)) {
            return true; // User is verified
        }
        return false; // Invalid credentials
    }
}