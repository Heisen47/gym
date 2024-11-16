package com.example.gym.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipDTO {
    private Long id;
    private Long userId;
    private Date paymentDate;
    private Date dueDate;
}
