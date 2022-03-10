package com.sjarno.norascoffeeshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
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

    List<UserRole> roles;

    @BeforeEach
    void setUp() {
        this.newUser = new UserAccount();
        userAccountService.createUserAdmin();
        this.roles = this.userRoleService.getAllUserRoleTypes();
    }

    @Test
    void testCreateUserAdmin() {
        assertEquals(1, userAccountRepository.findAll().size());
    }

    @Test
    void findAllUsersByUserRole() {

        assertEquals(3, roles.size());
        List<UserAccount> adminUsers = this.userAccountService.findUsersByUserRole(roles.get(0));
        assertEquals(1, adminUsers.size());
        assertEquals("admin-nora", adminUsers.get(0).getUsername());
        assertEquals(RoleType.ROLE_ADMIN, adminUsers.get(0).getRoles().get(0).getRoleType());
    }

    @Test
    void testFindByUsername() {
        this.newUser.setUsername("Mikko");
        this.newUser.setPassword("password");
        this.newUser.setRoles(new ArrayList<>());
        this.userAccountService.saveUserAccount(this.newUser);

        assertEquals(3, this.userRoleService.getAllUserRoleTypes().size());
        assertEquals(2, this.userAccountRepository.findAll().size());
        UserAccount userByName = this.userAccountService.getUserByUsername("Mikko");
        assertEquals(9, userByName.getId());
        assertEquals(this.newUser.getUsername(), userByName.getUsername());
    }

    @Test
    void testFindByUsernameThrowsExcpetion() {
        Exception exception = assertThrows(Exception.class, () -> {
            this.userAccountService.getUserByUsername("nameNotInDatabase");
        });
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testFindUserAccountById() {
        this.newUser.setUsername("MikkoId");
        this.newUser.setPassword("password");

        this.userAccountService.saveUserAccount(this.newUser);

        UserAccount userById = this.userAccountService.getUserById(9L);
        assertEquals(this.newUser.getUsername(), userById.getUsername());
    }

    @Test
    void findByIdThrowsException() {
        Exception exception = assertThrows(Exception.class, () -> {
            UserAccount userAccount = this.userAccountService.getUserById(6L);
        });
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void findByIdAndRole() {
        UserAccount adminUser = this.userAccountService.getUserByIdAndRole(this.roles.get(0), 8L);
        assertEquals(8, adminUser.getId());
        assertEquals("admin-nora", adminUser.getUsername());
        assertEquals(RoleType.ROLE_ADMIN, adminUser.getRoles().get(0).getRoleType());
    }

    @Test
    void findByIdAndRoleThrowsExcpetion() {
        Exception exception = assertThrows(Exception.class, () -> {
            this.userAccountService.getUserByIdAndRole(this.roles.get(0), 9L);
        });
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void findByUsernameAndRole() {
        UserAccount adminUser = this.userAccountService
            .getUserByUsernameAndRole("admin-nora", this.roles.get(0));
        assertEquals(8, adminUser.getId());
        assertEquals("admin-nora", adminUser.getUsername());
        assertEquals(RoleType.ROLE_ADMIN, adminUser.getRoles().get(0).getRoleType());
    }
    @Test
    void findByUsernameAndRoleThrowsException() {
        Exception exception = assertThrows(Exception.class, () -> {
            this.userAccountService.getUserByUsernameAndRole("admin-nor", this.roles.get(0));
        });
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    @WithMockUser(username = "admin-nora")
    void testGetUserAccountData() {
        assertEquals("admin-nora", userAccountService.getUserAccountData().getUsername());
        UserAccount userAccount = userAccountRepository.findByUsername("admin-nora").get();
        UserAccount accountFromService = userAccountService.getUserAccountData();
        assertEquals(userAccount.getId(), accountFromService.getId());
    }

    @Test
    @WithMockUser(username = "admin-nora")
    void testUpdateUsername() throws Exception {
        assertEquals("admin-nora", userAccountService.getUserAccountData().getUsername());
        userAccountService.updateUsername("Mikko");
        assertEquals("Mikko", userAccountRepository.findByUsername("Mikko").get().getUsername());
        assertEquals(1, userAccountRepository.findAll().size());
    }

    @Test
    @WithMockUser(username = "admin-nora")
    void takenUsernameThrowsError() {
        Exception usernameTakenError = assertThrows(Exception.class, () -> {
            userAccountService.updateUsername("admin-nora");
        });
        assertEquals("Username taken.", usernameTakenError.getMessage());
    }

    @Test
    @WithAnonymousUser
    void anonymousUserCannotFindUsername() {
        Exception usernameNotFound = assertThrows(UsernameNotFoundException.class, () -> {
            userAccountService.updateUsername("user");
        });
        assertEquals("User not found", usernameNotFound.getMessage());
    }

    @Test
    void testInitialDataExists() {
        assertEquals(1, userAccountRepository.findAll().size());
        assertEquals("admin-nora", userAccountRepository.findByUsername("admin-nora").get().getUsername());
    }

    @Test
    @WithMockUser(username = "admin-nora")
    void testCanUpdatePassword() {
        UserAccount userAccount = this.userAccountService.getUserAccountData();
        assertTrue(passwordEncoder.matches("pass", userAccount.getPassword()));
        UserAccount updatedAccount = userAccountService.updatePassword("newPass", "pass");
        assertEquals("admin-nora", updatedAccount.getUsername());
    }

    @Test
    @WithMockUser(username = "admin-nora")
    void wrongCredentialsThrowsError() {
        Exception wrongCredentials = assertThrows(IllegalArgumentException.class, () -> {
            userAccountService.updatePassword("new", "wrongPass");
        });
        assertEquals("Wrong credentials", wrongCredentials.getMessage());
    }

    private UserAccount createTestUser(String name, String password, ArrayList<UserRole> list) {
        return this.userAccountService.saveUserAccount(
                new UserAccount(name, password, list));
    }

}
