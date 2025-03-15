package com.example.gym.controller;

import com.example.gym.dto.AdminRegistrationRequest;
import com.example.gym.dto.AuthRequest;
import com.example.gym.dto.AuthResponse;
import com.example.gym.exception.AuthExceptions.AdminAlreadyExistsException;
import com.example.gym.exception.AuthExceptions.InvalidCredentialsException;
import com.example.gym.model.Admin;
import com.example.gym.service.AdminService;
import com.example.gym.util.JwtUtil;
import com.example.gym.dto.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AdminService adminService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, AdminService adminService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.adminService = adminService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            final UserDetails userDetails = adminService.loadUserByUsername(authRequest.getUsername());
            Admin admin = adminService.findByAdminEmail(authRequest.getUsername());
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            final String jwt = jwtUtil.generateToken(userDetails.getUsername(), admin.getAdminName() , roles);
            final Date expiryDate = jwtUtil.getExpirationDateFromToken(jwt);

            return ResponseEntity.ok(new AuthResponse(jwt , expiryDate , roles));

        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminRegistrationRequest request) {
        if (request.getEmail() == null || request.getPassword() == null ||
                request.getMobile() == null || request.getName() == null) {
            return ResponseEntity.badRequest().body("All fields are required");
        }

        adminService.registerAdmin(request.getEmail(), request.getPassword(),
                request.getMobile(), request.getName());
        return ResponseEntity.ok("Admin registered successfully");
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentials(InvalidCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("Authentication failed", e.getMessage()) {
                });
    }

    @ExceptionHandler(AdminAlreadyExistsException.class)
    public ResponseEntity<?> handleAdminExists(AdminAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("Registration failed", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error", e.getMessage()));
    }
}