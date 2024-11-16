package com.example.gym.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
public class homepage {

    @GetMapping("/home")
    public  ResponseEntity<String> homeController(){
        return new ResponseEntity<>("hello world", HttpStatus.OK);
    }
}
