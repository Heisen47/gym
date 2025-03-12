package com.example.gym.service;

import com.example.gym.model.Admin;
import com.example.gym.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerAdmin(String username, String password) {
        Admin admin = new Admin();
        admin.setAdminName(username);
        admin.setAdminPassword(passwordEncoder.encode(password));
        adminRepository.save(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String adminEmail) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByAdminEmail(adminEmail);
        if (admin == null) {
            throw new UsernameNotFoundException("Admin not found");
        }
        return User.builder()
                .username(admin.getAdminEmail())
                .password(admin.getAdminPassword())
                .roles("ADMIN")
                .build();
    }

    public boolean authenticateAdmin(String adminEmail, String adminPassword) {
        return adminRepository.findByAdminEmailAndAdminPassword(adminEmail, adminPassword) != null;
    }
}