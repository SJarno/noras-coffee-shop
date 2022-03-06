package com.sjarno.norascoffeeshop.services;

import java.util.ArrayList;
import java.util.Optional;

import com.sjarno.norascoffeeshop.models.RoleType;
import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.models.UserRole;
import com.sjarno.norascoffeeshop.repositories.UserAccountRepository;
import com.sjarno.norascoffeeshop.repositories.UserRoleRepository;
import com.sjarno.norascoffeeshop.security.SecurityContextService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    private UserRoleService userRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityContextService securityContextService;

    /* For quick testing */
    @Transactional
    public void createUserAdmin() {
        userRoleRepository.deleteAll();
        userAccountRepository.deleteAll();

        userRoleService.addBasicRoleTypes();

        UserAccount newUser = new UserAccount(
                "admin-nora",
                passwordEncoder.encode("pass"),
                new ArrayList<>());

        Optional<UserRole> role = this.userRoleRepository.findByRoleType(RoleType.ROLE_ADMIN);

        newUser.getRoles().add(role.get());
        this.saveUserAccount(newUser);

    }
    public UserAccount saveUserAccount(UserAccount userAccount) {
        this.validateUsername(userAccount.getUsername());
        this.validatePassword(userAccount.getPassword());
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        return this.userAccountRepository.save(userAccount);
    }


    /* Default option for getting authenticated user data */
    public UserAccount getUserAccountData() {
        Optional<UserAccount> userAccount = this.userAccountRepository.findByUsername(
                securityContextService.getSecurityContext().getName());
        if (userAccount.isPresent()) {
            return userAccount.get();
        }
        throw new UsernameNotFoundException("User not found");
    }
    /* Find all user by user role */

    @Transactional
    public UserAccount updateUsername(String newUsername) throws Exception {
        validateUsername(newUsername);
        UserAccount existingUser = getUserAccountData();
        existingUser.setUsername(newUsername.trim());
        return existingUser;
    }

    @Transactional
    public UserAccount updatePassword(String newPassword, String oldPassword) {
        UserAccount existingUser = getUserAccountData();
        if (passwordEncoder.matches(oldPassword, existingUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(newPassword));
            return existingUser;
        }
        throw new IllegalArgumentException("Wrong credentials");

    }

    private boolean checkIfUsernameTaken(String username) {
        Optional<UserAccount> userAccount = this.userAccountRepository.findByUsername(username);
        return userAccount.isPresent();
    }

    private void validateUsername(String username) {
        if (checkIfUsernameTaken(username)) {
            throw new DataIntegrityViolationException("Username taken.");
        }
        if (username.isBlank()) {
            throw new DataIntegrityViolationException("Username cannot be empty");
        }
        if (username.length() < 4 || username.length() > 40) {
            throw new DataIntegrityViolationException("Username length violation");
        }

    }

    private void validatePassword(String password) {
        if (password.isBlank()) {
            throw new DataIntegrityViolationException("Password cannot be empty");
        }
        if (password.length() < 4 || password.length() > 100) {
            throw new DataIntegrityViolationException("Password length violation");
        }
    }

}
