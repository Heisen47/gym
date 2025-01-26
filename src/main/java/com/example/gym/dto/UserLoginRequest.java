package com.example.gym.dto;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserLoginRequest {
    private String email;
    private String password;

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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


