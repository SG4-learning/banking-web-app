package com.example.banking.controller;

import com.example.banking.model.User;
import com.example.banking.service.UserService;
import com.example.banking.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowLoginForm() {
        String viewName = authController.showLoginForm(model, null);
        assertEquals("login", viewName);
        verify(model).addAttribute(eq("user"), any(User.class));
    }

    @Test
    public void testLoginUser_Success() {
        User user = new User();
        user.setName("testUser");
        user.setPassword("testPassword");

        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("testToken");

        String viewName = authController.loginUser(user, model, session);

        assertEquals("redirect:/dashboard", viewName);
        verify(session).setAttribute("jwt", "testToken");
        verify(session).setAttribute("user", user);
    }


    public void testLoginUser_AuthenticationException() {
        User user = new User();
        user.setName("testUser");
        user.setPassword("testPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(AuthenticationException.class);

        String viewName = authController.loginUser(user, model, session);

        assertEquals("login", viewName);
        verify(model).addAttribute("error", "Invalid username or password");
    }

    @Test
    public void testLoginUser_Exception() {
        User user = new User();
        user.setName("testUser");
        user.setPassword("testPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(RuntimeException.class);

        String viewName = authController.loginUser(user, model, session);

        assertEquals("login", viewName);
        verify(model).addAttribute("error", "An error occurred");
    }

    @Test
    public void testLogout() {
        String viewName = authController.logout(null, null, session);
        assertEquals("redirect:/login?logoutSuccess=true", viewName);
        verify(session).invalidate();
    }

    @Test
    public void testShowRegistrationForm() {
        String viewName = authController.showRegistrationForm(model);
        assertEquals("register", viewName);
        verify(model).addAttribute(eq("user"), any(User.class));
    }

    @Test
    public void testRegisterUser_Success() {
        User user = new User();
        user.setName("testUser");

        String viewName = authController.registerUser(user, model);

        assertEquals("redirect:/login?success=true", viewName);
        verify(userService).saveUser(user);
    }

    @Test
    public void testRegisterUser_Exception() {
        User user = new User();
        user.setName("testUser");

        doThrow(RuntimeException.class).when(userService).saveUser(any(User.class));

        String viewName = authController.registerUser(user, model);

        assertEquals("redirect:/login", viewName);
        verify(model).addAttribute("error", "An error occurred during registration. Please try again.");
    }
}