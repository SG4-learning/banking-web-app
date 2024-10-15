package com.example.banking.controller;

import com.example.banking.exception.UserAlreadyExistsException;
import com.example.banking.model.User;
import com.example.banking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userService.userExists(user.getName())) {
            throw new UserAlreadyExistsException("User already exists with username: " + user.getName());
        }
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userService.userExists(user.getName())) {
            throw new UserAlreadyExistsException("User already exists with username: " + user.getName());
        }
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
}