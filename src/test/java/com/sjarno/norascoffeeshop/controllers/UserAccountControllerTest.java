package com.sjarno.norascoffeeshop.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
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
        String username = "nora";
        Map<String, String> jsonResult = new HashMap<>();
        jsonResult.put("username", username);
        
        MvcResult result = this.updateUsername(username).andExpect(status().isOk()).andReturn();

        assertEquals(new JSONObject(jsonResult).toString(), result.getResponse().getContentAsString());
    }
    @Test
    @WithMockUser(username = "admin-nora", password = "pass")
    void testUpdateUsernameWithWrongValesReturnsError() throws Exception {
        String username = "nor";
        Map<String, String> jsonResult = new HashMap<>();
        jsonResult.put("username", username);

        MvcResult result = this.updateUsername(username).andExpect(status().isUnprocessableEntity()).andReturn();
        assertEquals("Username must be between 4 and 40 characters", result.getResponse().getContentAsString());
    }

    @Test
    @WithAnonymousUser
    void anonymousUserCannotUpdateUsername() throws Exception {
        String username = "nora";
        Map<String, String> jsonResult = new HashMap<>();
        jsonResult.put("username", username);

        MvcResult result = this.updateUsername("nora").andExpect(status().isForbidden()).andReturn();

        //assertEquals(new JSONObject(jsonResult).toString(), result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "admin-nora", password = "pass")
    void testUpdatePassword() throws Exception {
        
        MvcResult result = this.updatePassword("newPass", "pass")
            .andExpect(status().isOk()).andReturn();
        //assertEquals("expected", result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "admin-nora")
    void testUpdatePasswordWithWrongValueReturnsError() throws Exception {
        MvcResult result = this.updatePassword("pas", "pass")
            .andExpect(status().isUnprocessableEntity()).andReturn();
        assertEquals("Password length violation", result.getResponse().getContentAsString());
    }


    private ResultActions updateUsername(String newUsername) throws Exception {
        return this.mockMvc.perform(put("/update-username")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_NDJSON_VALUE)
            .content(newUsername));
            
    }
    private ResultActions updatePassword(String newPassword, String oldPassword) throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("newPassword", newPassword);
        requestParams.add("oldPassword", oldPassword);

        return this.mockMvc.perform(put("/update-password")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .params(requestParams));
    }
}
