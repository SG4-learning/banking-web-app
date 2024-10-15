package com.example.banking.service;

import com.example.banking.controller.AccountController;
import com.example.banking.model.Account;
import com.example.banking.model.User;
import com.example.banking.repository.AccountRepository;
import com.example.banking.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    public Account getAccountDetails(String username) {
        User user = userRepository.findByName(username);
        if (user != null) {
            return accountRepository.findByUserId(user.getId());
        }
        return null;
    }

    public void createAccount(Account account, User user) {
        account.setUser(user);
        accountRepository.save(account);
    }

    public void createAccount(Account account) {
        logger.info("Creating a new account: " + account);
        User user = userRepository.findByName(account.getUser().getName());
        logger.info("User name: {}",user.getName());
        logger.info("User id: {}",user.getId());
        if (user != null && user.getId() == null) {
            user = userRepository.save(user); // Ensure the user is persisted
        }
        account.setUser(user);
        
        logger.info("Account: {}",account.toString());
        accountRepository.save(account);
    }

    public Account findById(Long id) {
        logger.info("Finding account by id: " + id);
        return accountRepository.findById(id).orElse(null);
    }
}