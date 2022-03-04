package com.sjarno.norascoffeeshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
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
    @WithMockUser(username = "mikko")
    void testCreateEmployee() {
        this.newUser.setUsername("mikko");
        this.newUser.setPassword("passu");
        this.userAccountService.createNewEmployee(this.newUser);
        assertEquals(1, this.userAccountRepository.findAll().size());
        assertEquals(2, this.userAccountService.getUserAccountData().getId());
        assertEquals("mikko", this.userAccountService.getUserAccountData().getUsername());
        assertEquals(
            new ArrayList<>(Arrays.asList(new UserRole(RoleType.ROLE_EMPLOYEE))), 
            this.userAccountService.getUserAccountData().getRoles());
    }
    @Test
    @WithMockUser(username = "mik")
    void testWrongValuesThrowsException() {
        Exception usernameLengthException = assertThrows(Exception.class, () -> {
            this.newUser.setUsername("mik");
            this.newUser.setPassword("password");
            this.userAccountService.createNewEmployee(this.newUser);
        });
        assertEquals("Username length violation", usernameLengthException.getMessage());

        Exception usernameEmptyException = assertThrows(Exception.class, () -> {
            this.newUser.setUsername("");
            this.userAccountService.createNewEmployee(this.newUser);
        });
        assertEquals("Username cannot be empty", usernameEmptyException.getMessage());

        Exception passwordLengthException = assertThrows(Exception.class, () -> {
            this.newUser.setUsername("username");
            this.newUser.setPassword("pas");
            this.userAccountService.createNewEmployee(this.newUser);
        });
        assertEquals("Password length violation", passwordLengthException.getMessage());

        Exception passwordNotEmptyException = assertThrows(Exception.class, () -> {
            this.newUser.setUsername("username");
            this.newUser.setPassword("");
            this.userAccountService.createNewEmployee(this.newUser);
        });
        assertEquals("Password cannot be empty", passwordNotEmptyException.getMessage());
    }

    @Test
    void addingSameUsernameThrowsException() {
        assertEquals(0, this.userAccountRepository.findAll().size());
        Exception ex = assertThrows(Exception.class, () -> {
            this.newUser.setUsername("mikko");
            this.newUser.setPassword("passu");
            this.userAccountService.createNewEmployee(this.newUser);
            UserAccount userAccount = new UserAccount();
            userAccount.setUsername("mikko");
            userAccount.setPassword("pass");
            this.userAccountService.createNewEmployee(userAccount);
        });
        assertEquals(1, this.userAccountRepository.findAll().size());
        assertEquals("Username taken.", ex.getMessage());
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
        UserAccount user = userAccountService.updatePassword("new", "pass-nora");
        assertEquals("admin-nora", user.getUsername());
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
