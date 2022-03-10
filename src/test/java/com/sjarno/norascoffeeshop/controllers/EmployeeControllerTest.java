package com.sjarno.norascoffeeshop.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjarno.norascoffeeshop.models.RoleType;
import com.sjarno.norascoffeeshop.models.UserAccount;

import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
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

import net.minidev.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Map;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UserAccount employyAccount;

    @BeforeEach
    void setUp() {
        this.employyAccount = new UserAccount();
        this.employyAccount.setUsername("Mikko");
        this.employyAccount.setPassword("pass");
         
    }

    @Test
    @WithMockUser(username = "admin-nora", password = "pass")
    void testCreateNewEmployee() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/employee/create")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .content(objectMapper.writeValueAsString(this.employyAccount))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        UserAccount createdEmpAccount = objectMapper.readValue(jsonResult, UserAccount.class);
        assertEquals(5, createdEmpAccount.getId());
        assertEquals("Mikko", createdEmpAccount.getUsername());
        assertEquals(RoleType.ROLE_EMPLOYEE, createdEmpAccount.getRoles().get(0).getRoleType());

    }
}
