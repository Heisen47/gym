package com.example.gym.exception.AuthExceptions;

public class AdminAlreadyExistsException extends RuntimeException{

    public AdminAlreadyExistsException(String message) {
        super(message);
    }
}
