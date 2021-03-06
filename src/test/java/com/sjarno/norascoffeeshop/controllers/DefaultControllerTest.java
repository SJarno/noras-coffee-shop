package com.sjarno.norascoffeeshop.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class DefaultControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithAnonymousUser
    @WithMockUser
    void testServerGreeting() throws Exception {
        this.mockMvc.perform(get("/greet")).andExpect(status().isOk());

        MvcResult result = this.mockMvc.perform(get("/greet"))
            .andExpect(status().isOk()).andReturn();
        String jsonContent = "{\"greet\":\"Hi, and welcome! The site is currently under construction, but stay tuned for upcoming site.\"}";
        assertEquals(jsonContent, result.getResponse().getContentAsString());
    }
}
