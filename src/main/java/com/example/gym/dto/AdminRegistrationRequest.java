package com.example.gym.dto;

import lombok.Data;

@Data
public class AdminRegistrationRequest {
    private String email;
    private String password;
    private String mobile;
    private String name;
}