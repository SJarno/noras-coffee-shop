package com.sjarno.norascoffeeshop.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import com.sjarno.norascoffeeshop.models.RoleType;
import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.models.UserRole;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
public class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    UserAccount userAccount;
    UserAccount notSavedAccount;

    @BeforeEach
    void setUp() {
        userAccount = new UserAccount();
        userAccount.setUsername("user");
        userAccount.setPassword("pass");
        UserRole role = new UserRole();
        role.setRoleType(RoleType.ROLE_ADMIN);
        userRoleRepository.save(role);
        userAccount.setRoles(new ArrayList<>(Arrays.asList(role)));
        

        notSavedAccount = new UserAccount();
        notSavedAccount.setUsername("user");
        notSavedAccount.setPassword("pass");
        notSavedAccount.setRoles(Arrays.asList(role));

        userAccountRepository.save(userAccount);

    }

    @Test
    void testFindByUsername() {
        assertEquals(1, this.userAccountRepository.findAll().size());
        assertEquals(userAccount, this.userAccountRepository.findByUsername("user").get());
        assertEquals(2, this.userAccountRepository.findByUsername("user").get().getId());
    }
}
