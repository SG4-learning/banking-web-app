package com.example.banking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private String description;
    private BigDecimal amount;

    // Constructors, getters, and setters

    public Transactions() {
    }

    public Transactions(LocalDateTime timestamp, String description, BigDecimal amount) {
        this.timestamp = timestamp;
        this.description = description;
        this.amount = amount;
    }

   
}