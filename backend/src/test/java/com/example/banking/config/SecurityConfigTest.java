package com.example.banking.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testStaticResources() throws Exception {
        mockMvc.perform(get("/css/styles.css")).andExpect(status().isOk());
        mockMvc.perform(get("/js/scripts.js")).andExpect(status().isOk());
    }

    @Test
    public void testPublicEndpoints() throws Exception {
        mockMvc.perform(get("/login")).andExpect(status().isOk());
        mockMvc.perform(get("/register")).andExpect(status().isOk());
    }

    @Test
    public void testProtectedEndpointsWithoutAuthentication() throws Exception {
    ;
        mockMvc.perform(get("/fund-transfer")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/transaction-history")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/settings")).andExpect(status().isUnauthorized());
    }

}