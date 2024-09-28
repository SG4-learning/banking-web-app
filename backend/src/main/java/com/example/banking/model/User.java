package com.example.banking.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String phone;
    private String email;

    public User() {
    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    // Getters and setters

    
}