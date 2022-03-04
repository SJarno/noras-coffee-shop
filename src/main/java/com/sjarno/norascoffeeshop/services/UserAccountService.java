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
                "admin-nora",
                passwordEncoder.encode("pass-nora"),
                new ArrayList<>(Arrays.asList(role)));

        userAccountRepository.save(newUser);
    }

    public void createNewEmployee(UserAccount employee) {
            validateUsername(employee.getUsername());
            validatePassword(employee.getPassword());
            UserRole employeeRole = new UserRole();
            employeeRole.setRoleType(RoleType.ROLE_EMPLOYEE);
            this.userRoleRepository.save(employeeRole);
            employee.setRoles(new ArrayList<>(Arrays.asList(employeeRole)));
            userAccountRepository.save(employee);
        
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
