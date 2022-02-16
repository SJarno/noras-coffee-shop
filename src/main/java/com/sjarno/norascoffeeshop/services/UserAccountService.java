package com.sjarno.norascoffeeshop.services;

import java.util.ArrayList;
import java.util.Arrays;

import com.sjarno.norascoffeeshop.models.RoleType;
import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.models.UserRole;
import com.sjarno.norascoffeeshop.repositories.UserAccountRepository;
import com.sjarno.norascoffeeshop.repositories.UserRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /* For quick testing */
    public void createUserAdmin() {
        UserRole role = new UserRole();
        role.setRoleType(RoleType.ROLE_ADMIN);
        
        userRoleRepository.save(role);
        UserAccount newUser = new UserAccount(
            "user", 
            passwordEncoder.encode("pass"),
            new ArrayList<>(Arrays.asList(role)));
        userAccountRepository.save(newUser);
    }
    /* Read admin data */

    /* Update admin data */

    /* Create employee */
    /* Read emp data */
    /* Update emp data */
    /* Delete emp */

    /* Create/register as customer */
    /* Read customer data */
    /* Update customer data */
    /* Delete customer */
    
}
