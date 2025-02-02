package com.example.gym.service;

import com.example.gym.exception.DuplicateEmailException;
import com.example.gym.exception.DuplicatePhoneNumberException;
import com.example.gym.model.User;
import com.example.gym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        Optional<User> existingPhoneNumber = userRepository.findByPhoneNumber(user.getPhoneNumber());
        Optional<User> existingEmail = userRepository.findByEmail(user.getEmail());

        if (existingEmail.isPresent()) {
            throw new DuplicateEmailException("Email already exists");
        }
        if (existingPhoneNumber.isPresent()) {
            throw new DuplicatePhoneNumberException("Phone number already exists");
        }
        return userRepository.save(user);
    }

    public Optional<User> getSingleUser(Long id) {
        return userRepository.findById(id);
    }

}