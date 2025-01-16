package com.example.gym.controller;

import com.example.gym.dto.UserLoginRequest;
import com.example.gym.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/v1/api/users")
public class Login {

    @Autowired
    private UserService userService;

    @PostMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestBody UserLoginRequest request) {
        log.info("Request received with username: {}", request.getEmail());
        boolean isVerified = userService.verifyUser(request.getEmail(), request.getPassword());
        if (isVerified) {
            return ResponseEntity.ok("User verified successfully!");
        }
        return ResponseEntity.status(401).body("Invalid username or password");
    }
}
