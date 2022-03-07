package com.sjarno.norascoffeeshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.sjarno.norascoffeeshop.models.RoleType;
import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.models.UserRole;
import com.sjarno.norascoffeeshop.repositories.UserAccountRepository;

import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    UserAccount newUser;

    @BeforeEach
    void setUp() {
        this.newUser = new UserAccount();
    }

    @Test
    void testCreateUserAdmin() {
        userAccountService.createUserAdmin();
        assertEquals(1, userAccountRepository.findAll().size());
    }

    @Test
    void findByUserRole() {
        userAccountService.createUserAdmin();
        List<UserRole> roles = this.userRoleService.getAllUserRoleTypes();
        assertEquals(3, roles.size());
        List<UserAccount> adminUsers = this.userAccountService.findUsersByUserRole(roles.get(0));
        assertEquals(1, adminUsers.size());
        assertEquals("admin-nora", adminUsers.get(0).getUsername());
        assertEquals(RoleType.ROLE_ADMIN, adminUsers.get(0).getRoles().get(0).getRoleType());
    }

    @Test
    @WithMockUser(username = "admin-nora")
    void testGetUserAccountData() {
        userAccountService.createUserAdmin();
        assertEquals("admin-nora", userAccountService.getUserAccountData().getUsername());
        UserAccount userAccount = userAccountRepository.findByUsername("admin-nora").get();
        UserAccount accountFromService = userAccountService.getUserAccountData();
        assertEquals(userAccount.getId(), accountFromService.getId());
    }

    @Test
    @WithMockUser(username = "admin-nora")
    void testUpdateUsername() throws Exception {
        userAccountService.createUserAdmin();
        assertEquals("admin-nora", userAccountService.getUserAccountData().getUsername());
        userAccountService.updateUsername("Mikko");
        assertEquals("Mikko", userAccountRepository.findByUsername("Mikko").get().getUsername());
        assertEquals(1, userAccountRepository.findAll().size());
    }

    @Test
    @WithMockUser(username = "admin-nora")
    void takenUsernameThrowsError() {
        userAccountService.createUserAdmin();
        Exception usernameTakenError = assertThrows(Exception.class, () -> {
            userAccountService.updateUsername("admin-nora");
        });
        assertEquals("Username taken.", usernameTakenError.getMessage());
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
        assertEquals("admin-nora", userAccountRepository.findByUsername("admin-nora").get().getUsername());
    }

    @Test
    @WithMockUser(username = "admin-nora")
    void testCanUpdatePassword() {
        userAccountService.createUserAdmin();
        UserAccount userAccount = this.userAccountService.getUserAccountData();
        assertTrue(passwordEncoder.matches("pass", userAccount.getPassword()));
        UserAccount updatedAccount = userAccountService.updatePassword("newPass", "pass");
        assertEquals("admin-nora", updatedAccount.getUsername());
    }

    @Test
    @WithMockUser(username = "admin-nora")
    void wrongCredentialsThrowsError() {
        userAccountService.createUserAdmin();
        Exception wrongCredentials = assertThrows(IllegalArgumentException.class, () -> {
            userAccountService.updatePassword("new", "wrongPass");
        });
        assertEquals("Wrong credentials", wrongCredentials.getMessage());
    }

}
