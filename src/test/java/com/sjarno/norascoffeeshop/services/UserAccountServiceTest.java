package com.sjarno.norascoffeeshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.repositories.UserAccountRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserAccountServiceTest {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    void testCreateUserAdmin() {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername("Mikko");
    }

    @Test
    @WithMockUser
    void testGetUserAccountData() {
        assertEquals("user", userAccountService.getUserAccountData().getUsername());
        UserAccount userAccount = userAccountRepository.findByUsername("user").get();
        UserAccount accountFromService = userAccountService.getUserAccountData();
        assertEquals(userAccount.getId(), accountFromService.getId());
    }

    @Test
    @WithMockUser
    void testUpdateUsername() throws Exception {
        assertEquals("user", userAccountService.getUserAccountData().getUsername());
        userAccountService.updateUsername("Mikko");
        assertEquals("Mikko", userAccountRepository.findByUsername("Mikko").get().getUsername());
        assertEquals(1, userAccountRepository.findAll().size());
        
        
    }
    @Test
    void testInitialDataExists() {
        assertEquals(1, userAccountRepository.findAll().size());
        assertEquals("user", userAccountRepository.findByUsername("user").get().getUsername());
    }
}
