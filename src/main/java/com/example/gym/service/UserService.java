package com.example.gym.service;

import com.example.gym.exception.DuplicateEmailException;
import com.example.gym.exception.DuplicatePhoneNumberException;
import com.example.gym.model.User;
import com.example.gym.repository.UserRepository;
import jakarta.validation.Valid;
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

    //method to get all users -----------------------------------------------------------
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //method to add a new user -----------------------------------------------------------
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

    //method to update a user details -----------------------------------------------------------
    public Optional<User> updateUser(Long id, User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();

            updatedUser.setName(user.getName());

            updatedUser.setEmail(user.getEmail());

            updatedUser.setPhoneNumber(user.getPhoneNumber());

            updatedUser.setMembership(user.getMembership());


            return Optional.of(userRepository.save(updatedUser));
        }
        return Optional.empty();
    }

    // method to handle image updates -----------------------------------------------------------
    public Optional<User> updateUserImage(Long id, byte[] image) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setImage(image);
            return Optional.of(userRepository.save(updatedUser));
        }
        return Optional.empty();
    }

    public boolean deleteUser(Long id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}