package com.example.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.banking.model.Account;
import com.example.banking.model.User;
import com.example.banking.service.AccountService;
import com.example.banking.service.UserService;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class PageController {

    private static final Logger logger = LoggerFactory.getLogger(PageController.class);

     @Autowired
    private AccountService accountService;

      @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
       // Retrieve the authenticated user details
       logger.info("Calling dashboard :::Getting user details");
       // Retrieve user details from session
       User user = (User) session.getAttribute("user");
       if (user == null) {
           return "redirect:/login"; // Redirect to login if user details are not found
       }
      
       Account account = accountService.getAccountDetails(user.getName());
       model.addAttribute("user", user);
       model.addAttribute("account", account);

       return "dashboard";
    }



    @GetMapping("/fund-transfer")
    public String fundTransfer() {
        return "fund-transfer";
    }

    @GetMapping("/transaction-history")
    public String transactionHistory() {
        return "transaction-history";
    }

    @GetMapping("/settings")
    public String settings() {
        return "settings";
    }
}