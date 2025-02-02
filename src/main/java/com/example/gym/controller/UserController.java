package com.example.gym.controller;

import com.example.gym.exception.ImageSizeException;
import com.example.gym.model.User;
import com.example.gym.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    //All users-----------------------------------------------------------------------------------------------

    @GetMapping("/customers")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //create user ----------------------------------------------------------------------------------------

    @PostMapping(value = "/addUser" , consumes = {"multipart/form-data"})
    public ResponseEntity<User> addUser( @RequestParam("name") String name,
                                         @RequestParam("email") String email,
                                         @RequestParam("phoneNumber") String phoneNumber,
                                         @RequestParam("membership") Boolean membership,
                                         @RequestParam("image") MultipartFile image) throws IOException {

        String contentType = image.getContentType();
        if(contentType == null || !contentType.startsWith("image/")){
            return ResponseEntity.badRequest().body(null);
        }

        long maxSize = 2 * 1024 * 1024; // 2MB
        if (image.getSize() > maxSize) {
            ResponseEntity.badRequest();
            throw new ImageSizeException("Image size should be less than 2MB");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setMembership(membership);
        user.setImage(image.getBytes());

        User newUser = userService.addUser(user);
        return ResponseEntity.ok(newUser);
    }

//    single user----------------------------------------------------------------------------------------------

    @GetMapping("/customers/{id}")
    public ResponseEntity<User> getSingleUser(@PathVariable Long id){
        return userService.getSingleUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}