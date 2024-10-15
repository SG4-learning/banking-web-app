package com.example.banking.controller;

import com.example.banking.model.User;
import com.example.banking.model.Account;
import com.example.banking.service.AccountService;
import com.example.banking.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/new-account")
    public String showCreateAccountForm(Model model) {
        logger.info("Calling showCreateAccountForm :::Showing create account form");
        return "new-account"; // Return the create account form
    }

    @PostMapping("/new-account")
    public String createAccount(@RequestParam("accountNumber") String accountNumber,
                                @RequestParam("accountType") String accountType,
                                @RequestParam("balance") Double balance, 
                                HttpSession session, Model model) {
        logger.info("Calling createAccount :::Creating a new account");
        // Retrieve user details from session
        User user = (User) session.getAttribute("user");
        logger.info("User details: " + user);
        if (user == null) {
            return "redirect:/login"; // Redirect to login if user details are not found
        }

        // Create a new account for the user
        Account account = new Account();
        account.setUser(user);
        account.setAccountNumber(accountNumber);
        account.setAccountType(accountType);
        account.setBalance(balance);
        accountService.createAccount(account);

        // Update session and model
        session.setAttribute("account", account);
        model.addAttribute("account", account);

        return "redirect:/account-details?accountId=" + account.getId(); // Redirect to the account details page
    }

    @GetMapping("/account-details")
    public String showAccountDetails(@RequestParam("accountId") Long accountId, Model model) {
       logger.info("Calling showAccountDetails :::Showing account details "+accountId);
        Account account = accountService.findById(accountId);
        logger.info("Account details: " + account.toString());
        if (account == null) {
            return "redirect:/dashboard"; // Redirect to dashboard if account is not found
        }
        model.addAttribute("account", account);
        return "account-details"; // Return the account details view
    }
}