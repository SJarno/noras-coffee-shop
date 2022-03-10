package com.sjarno.norascoffeeshop.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;

import net.minidev.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    

    @Test
    @WithMockUser(username = "admin-nora", password = "pass")
    void testUpdateUsername() throws Exception {
        
        String jsonContent = "{\"username\":\"nora\"}";

        MvcResult result = this.mockMvc.perform(put("/update-username")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content("nora"))
            .andExpect(status().isOk()).andReturn();

        
        assertEquals(jsonContent, result.getResponse().getContentAsString());
    }
    @Test
    @WithMockUser(username = "admin-nora", password = "pass")
    void testUpdatePassword() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("newPassword", "newPass");
        requestParams.add("oldPassword", "pass");

        MvcResult result = this.mockMvc.perform(put("/update-password")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .params(requestParams))
            .andExpect(status().isOk()).andReturn();
            

        //assertEquals("expected", result.getResponse().getContentAsString());
    }
}
