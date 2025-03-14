package com.example.gym.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AuthResponse {
    private String token;
    private Date expiryDate;
    private List<String> roles;

    public AuthResponse(String token , Date expiryDate , List<String> roles) {
        this.token = token;
        this.expiryDate=expiryDate;
        this.roles=roles;
    }
}