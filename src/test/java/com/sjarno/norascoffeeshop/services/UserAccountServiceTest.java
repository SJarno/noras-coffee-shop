package com.sjarno.norascoffeeshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.repositories.UserAccountRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
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
        userAccountService.createUserAdmin();
        assertEquals(1, userAccountRepository.findAll().size());
    }

    @Test
    @WithMockUser
    void testGetUserAccountData() {
        userAccountService.createUserAdmin();
        assertEquals("user", userAccountService.getUserAccountData().getUsername());
        UserAccount userAccount = userAccountRepository.findByUsername("user").get();
        UserAccount accountFromService = userAccountService.getUserAccountData();
        assertEquals(userAccount.getId(), accountFromService.getId());
    }

    @Test
    @WithMockUser
    void testUpdateUsername() throws Exception {
        userAccountService.createUserAdmin();
        assertEquals("user", userAccountService.getUserAccountData().getUsername());
        userAccountService.updateUsername("Mikko");
        assertEquals("Mikko", userAccountRepository.findByUsername("Mikko").get().getUsername());
        assertEquals(1, userAccountRepository.findAll().size());
    }
    @Test
    @WithMockUser
    void takenUsernameThrowsError() {
        userAccountService.createUserAdmin();
        Exception usernameTakenError = assertThrows(Exception.class, () -> {
            userAccountService.updateUsername("user");
        });
        assertEquals("Username is taken!", usernameTakenError.getMessage());
    }

    @Test
    @WithAnonymousUser
    void anonymousUserCannotFindUsername() {
        userAccountService.createUserAdmin();
        Exception usernameNotFound = assertThrows(UsernameNotFoundException.class, () -> {
            userAccountService.updateUsername("user");
        });
        assertEquals("User not found", usernameNotFound.getMessage());
    }

    @Test
    void testInitialDataExists() {
        userAccountService.createUserAdmin();
        assertEquals(1, userAccountRepository.findAll().size());
        assertEquals("user", userAccountRepository.findByUsername("user").get().getUsername());
    }
    @Test
    @WithMockUser
    void testCanUpdatePassword() {
        userAccountService.createUserAdmin();
        UserAccount user = userAccountService.updatePassword("new", "pass");
        assertEquals("user", user.getUsername());
    }
    @Test
    @WithMockUser
    void wrongCredentialsThrowsError() {
        userAccountService.createUserAdmin();
        Exception wrongCredentials = assertThrows(IllegalArgumentException.class, () -> {
            userAccountService.updatePassword("new", "wrongPass");
        });
        assertEquals("Wrong credentials", wrongCredentials.getMessage());
    }
}
