package com.example.gym.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;


@Entity
@Data
@Table(name = "UsersData")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number should be 10 digits")
    private String phoneNumber;

    @NotNull
    private Boolean membership;

    @Lob
    @NotNull
    private byte[] image;

    @Column(name = "Status")
    private Boolean active;

    @Column(name = "date_of_joining", columnDefinition = "TIMESTAMP")
    private ZonedDateTime dateOfJoining;

    @Column(name = "row_version", columnDefinition = "TIMESTAMP")
    private ZonedDateTime rowVersion;

    @PrePersist
    @PreUpdate
    public void setRowVersion() {
        this.rowVersion = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
    }
}
