package com.example.banking.controller;

import com.example.banking.model.User;


import com.example.banking.service.UserService;
import com.example.banking.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

     private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/login")
    public String showLoginForm(Model model,  @RequestParam(value = "success", required = false) String success) {
        model.addAttribute("user", new User());
        if (success != null) {
            model.addAttribute("success", "User registered successfully!");
        }
        return "login"; // This should match the name of the Thymeleaf template (login.html)
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model,  HttpSession session) {
        logger.info("@@@@@@@@@@@@@User login: {}", user.getName());
        logger.info("User password: {}", user.getPassword());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword())
            );
             SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("User authenticated: {}", user.getName());
        } catch (AuthenticationException e) {
            logger.error("Error authenticating user", e);
            model.addAttribute("error", "Invalid username or password");
            return "login";
        } catch (Exception e) {
            logger.error("Error authenticating user", e);
            model.addAttribute("error", "An error occurred");
            return "login";
        }
        final UserDetails userDetails = userService.loadUserByUsername(user.getName());
        logger.info("User details: {}", userDetails);
        final String jwt = jwtUtil.generateToken(userDetails);
        logger.info("Generated JWT: {}", jwt);

          // Store JWT token in session or cookie (for demonstration purposes, we'll use session)
          session.setAttribute("jwt", jwt);
          session.setAttribute("user", user);

        return "redirect:/dashboard"; // Redirect to the dashboard or another page
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        session.invalidate(); // Invalidate the session to log out the user
        return "redirect:/login?logoutSuccess=true"; // Redirect to login page with success message
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // This should match the name of the Thymeleaf template (register.html)
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.saveUser(user);
            logger.info("User registered: {}", user.getName());
            return "redirect:/login?success=true"; 
        } catch (Exception e) {
            logger.error("Error during registration for user: {}", user.getName(), e);
            model.addAttribute("error", "An error occurred during registration. Please try again.");
            return "redirect:/login";
        }
    }

}