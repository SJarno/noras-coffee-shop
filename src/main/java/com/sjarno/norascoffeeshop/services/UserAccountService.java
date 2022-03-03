package com.sjarno.norascoffeeshop.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import com.sjarno.norascoffeeshop.models.RoleType;
import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.models.UserRole;
import com.sjarno.norascoffeeshop.repositories.UserAccountRepository;
import com.sjarno.norascoffeeshop.repositories.UserRoleRepository;
import com.sjarno.norascoffeeshop.security.SecurityContextService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityContextService securityContextService;

    /* For quick testing */
    public void createUserAdmin() {
        
        userRoleRepository.deleteAll();
        userAccountRepository.deleteAll();
        UserRole role = new UserRole();
        role.setRoleType(RoleType.ROLE_ADMIN);
        
        userRoleRepository.save(role);
        UserAccount newUser = new UserAccount(
            "user", 
            passwordEncoder.encode("pass"),
            new ArrayList<>(Arrays.asList(role)));
        userAccountRepository.save(newUser);
    }
    public UserAccount getUserAccountData() {
        Optional<UserAccount> userAccount =  this.userAccountRepository.findByUsername(securityContextService.getSecurityContext().getName());
        if (userAccount.isPresent()) {
            return userAccount.get();
        }
        throw new UsernameNotFoundException("User not found");
    }
    @Transactional
    public UserAccount updateUsername(String newUsername) throws Exception {
        UserAccount existingUser = getUserAccountData();
        if (checkIfUsernameTaken(newUsername)) {
            throw new Exception("Username is taken!");
        }
        existingUser.setUsername(newUsername.trim());
        
        return existingUser;
    }
    private boolean checkIfUsernameTaken(String username) {
        Optional<UserAccount> userAccount = this.userAccountRepository.findByUsername(username);
        return userAccount.isPresent();
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
