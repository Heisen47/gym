package com.example.gym.dto;

import com.example.gym.model.AdminLogin;
import com.example.gym.repository.AdminLoginRepo;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserLoginRequest {
    private String email;
    private String password;

    // Getters and setters
    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

//public class verifyUser(String email, String password) {
//    AdminLogin adminLogin = AdminLoginRepo.findByUsername(email);
//    if (adminLogin != null) {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        return encoder.matches(password, adminLogin.getPassword());
//    }
//    return false;
//
//}


