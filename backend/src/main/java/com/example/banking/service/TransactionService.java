package com.example.banking.service;

import com.example.banking.model.Transactions;
import com.example.banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transactions> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transactions getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found with id: " + id));
    }

    public Transactions createTransaction(Transactions transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}