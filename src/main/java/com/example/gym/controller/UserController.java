package com.example.gym.controller;

import com.example.gym.dto.MembershipDTO;
import com.example.gym.dto.UserCreateDTO;
import com.example.gym.dto.UserDTO;
import com.example.gym.model.Membership;
import com.example.gym.model.User;
import com.example.gym.service.MembershipService;
import com.example.gym.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final MembershipService membershipService;

    @Autowired
    public UserController(UserService userService, MembershipService membershipService) {
        this.userService = userService;
        this.membershipService = membershipService;
    }


    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO){
        UserDTO createdUser = userService.createUser(userCreateDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email){
        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO
    ){
        UserDTO updatedUser = userService.updateUser(id , userDTO);
        return ResponseEntity.ok(updatedUser);
    }
}
