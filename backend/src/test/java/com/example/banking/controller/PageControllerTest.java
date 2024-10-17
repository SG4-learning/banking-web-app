package com.example.banking.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import com.example.banking.model.Account;
import com.example.banking.model.User;
import com.example.banking.service.AccountService;
import com.example.banking.service.UserService;

public class PageControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private UserService userService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private PageController pageController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDashboard_UserNotInSession() {
        when(session.getAttribute("user")).thenReturn(null);

        String viewName = pageController.dashboard(session, model);

        assertEquals("redirect:/login", viewName);
    }

    @Test
    public void testDashboard_UserInSession() {
        User user = new User();
        user.setName("testUser");
        Account account = new Account();

        when(session.getAttribute("user")).thenReturn(user);
        when(accountService.getAccountDetails("testUser")).thenReturn(account);

        String viewName = pageController.dashboard(session, model);

        assertEquals("dashboard", viewName);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("account", account);
    }

    @Test
    public void testFundTransfer() {
        String viewName = pageController.fundTransfer();

        assertEquals("fund-transfer", viewName);
    }

    @Test
    public void testTransactionHistory() {
        String viewName = pageController.transactionHistory();

        assertEquals("transaction-history", viewName);
    }

    @Test
    public void testSettings() {
        String viewName = pageController.settings();

        assertEquals("settings", viewName);
    }

    // Add more test methods for other controller methods if needed

}



