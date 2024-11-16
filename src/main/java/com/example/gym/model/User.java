package com.example.gym.model;


import com.example.gym.util.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
@Table(name = "users")
@ToString(exclude = "membership")
@EqualsAndHashCode(exclude = "membership")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "user" , cascade = CascadeType.ALL)
    private Membership membership;


}
