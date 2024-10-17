package com.example.banking.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;



@SpringBootTest
@AutoConfigureMockMvc
public class CustomErrorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    
    public void testHandleError400() throws Exception {
        mockMvc.perform(get("/error").requestAttr("javax.servlet.error.status_code", 400))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("message", "Bad Request"));
    }

    
    public void testHandleError401() throws Exception {
        mockMvc.perform(get("/error").requestAttr("javax.servlet.error.status_code", 401))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("message", "Unauthorized"));
    }

    
    public void testHandleError403() throws Exception {
        mockMvc.perform(get("/error").requestAttr("javax.servlet.error.status_code", 403))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("message", "Forbidden"));
    }

    
    public void testHandleError404() throws Exception {
        mockMvc.perform(get("/error").requestAttr("javax.servlet.error.status_code", 404))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("message", "Page Not Found"));
    }

    
    public void testHandleError500() throws Exception {
        mockMvc.perform(get("/error").requestAttr("javax.servlet.error.status_code", 500))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("message", "Internal Server Error"));
    }

}