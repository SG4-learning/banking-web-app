package com.example.banking.repository;

import com.example.banking.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Long> {
    // Add custom query methods if needed
}