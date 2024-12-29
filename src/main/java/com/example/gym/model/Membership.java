package com.example.gym.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Entity
@Data
@Table(name = "memberships")
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private AdminLogin user;

    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    @Temporal(TemporalType.DATE)
    private Date dueDate;
}
