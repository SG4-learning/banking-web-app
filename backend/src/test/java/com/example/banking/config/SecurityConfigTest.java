package com.example.banking.config;

import com.example.banking.security.CustomAccessDeniedHandler;
import com.example.banking.security.JwtAuthenticationEntryPoint;
import com.example.banking.security.JwtRequestFilter;
import com.example.banking.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SecurityConfig.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)
})
@ContextConfiguration(classes = {SecurityConfig.class, JwtAuthenticationEntryPoint.class, CustomAccessDeniedHandler.class})
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @MockBean
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testPublicEndpoints() throws Exception {
        mockMvc.perform(get("/login.html")).andExpect(status().isOk());
        mockMvc.perform(get("/register.html")).andExpect(status().isOk());
       // mockMvc.perform(get("/error")).andExpect(status().isOk());
       // mockMvc.perform(get("/favicon.ico")).andExpect(status().isOk());
    }

    @Test
    public void testStaticResources() throws Exception {
        mockMvc.perform(get("/css/styles.css")).andExpect(status().isOk());
        mockMvc.perform(get("/js/scripts.js")).andExpect(status().isOk());
    
    }

    @Test
    @WithMockUser
    public void testProtectedEndpointsWithAuthentication() throws Exception {
        mockMvc.perform(get("/dashboard.html")).andExpect(status().isOk());
        mockMvc.perform(get("/account-details.html")).andExpect(status().isOk());
        mockMvc.perform(get("/fund-transfer.html")).andExpect(status().isOk());
        mockMvc.perform(get("/transaction-history.html")).andExpect(status().isOk());
        //mockMvc.perform(get("/settings.html")).andExpect(status().isOk());
    }

    //@Test
    public void testProtectedEndpointsWithoutAuthentication() throws Exception {
       // mockMvc.perform(get("/dashboard.html")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/account-details.html")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/fund-transfer.html")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/transaction-history.html")).andExpect(status().isUnauthorized());
     } 
}