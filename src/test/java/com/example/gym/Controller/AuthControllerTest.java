package com.example.gym.Controller;

import com.example.gym.dto.AdminRegistrationRequest;
import com.example.gym.dto.AuthRequest;
import com.example.gym.exception.AuthExceptions.AdminAlreadyExistsException;
import com.example.gym.exception.AuthExceptions.InvalidCredentialsException;
import com.example.gym.model.Admin;
import com.example.gym.service.AdminService;
import com.example.gym.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private AdminService adminService;

    @Autowired
    private ObjectMapper objectMapper;

    private AuthRequest validAuthRequest;
    private AdminRegistrationRequest validRegRequest;
    private UserDetails userDetails;
    private Admin admin;

    @BeforeEach
    void setUp() {
        validAuthRequest = new AuthRequest();
        validAuthRequest.setUsername("admin@test.com");
        validAuthRequest.setPassword("password123");

        validRegRequest = new AdminRegistrationRequest();
        validRegRequest.setEmail("admin@test.com");
        validRegRequest.setPassword("password123");
        validRegRequest.setName("Test Admin");
        validRegRequest.setMobile("1234567890");

        userDetails = new User("admin@test.com", "password123",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        admin = new Admin();
        admin.setAdminEmail("admin@test.com");
        admin.setAdminName("Test Admin");
    }

    @Test
    void login_WithValidCredentials_ShouldReturnToken() throws Exception {
        // Arrange
        String token = "valid.jwt.token";
        Date expiryDate = new Date(System.currentTimeMillis() + 3600000);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
        when(adminService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(adminService.findByAdminEmail(anyString())).thenReturn(admin);
        when(jwtUtil.generateToken(anyString(), anyString(), any())).thenReturn(token);
        when(jwtUtil.getExpirationDateFromToken(token)).thenReturn(expiryDate);

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validAuthRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.expiryDate").exists())
                .andExpect(jsonPath("$.roles").isArray());
    }

    @Test
    void login_WithInvalidCredentials_ShouldReturn401() throws Exception {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new InvalidCredentialsException("Invalid username or password"));

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validAuthRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Authentication failed"));
    }

    @Test
    void registerAdmin_WithValidData_ShouldReturnSuccess() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRegRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Admin registered successfully"));
    }

    @Test
    void registerAdmin_WithMissingFields_ShouldReturnBadRequest() throws Exception {
        // Arrange
        validRegRequest.setEmail(null);

        // Act & Assert
        mockMvc.perform(post("/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRegRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("All fields are required"));
    }

    @Test
    void registerAdmin_WithExistingAdmin_ShouldReturnConflict() throws Exception {
        // Arrange
        doThrow(new AdminAlreadyExistsException("Admin already exists"))
                .when(adminService).registerAdmin(anyString(), anyString(), anyString(), anyString());

        // Act & Assert
        mockMvc.perform(post("/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRegRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Registration failed"))
                .andExpect(jsonPath("$.detail").value("Admin already exists"));
    }

    @Test
    void handleGenericError_ShouldReturn500() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Unexpected error"))
                .when(adminService).registerAdmin(anyString(), anyString(), anyString(), anyString());

        // Act & Assert
        mockMvc.perform(post("/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRegRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error"))
                .andExpect(jsonPath("$.detail").value("Unexpected error"));
    }
}