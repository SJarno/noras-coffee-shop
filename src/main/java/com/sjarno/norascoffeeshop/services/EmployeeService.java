package com.sjarno.norascoffeeshop.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import com.sjarno.norascoffeeshop.models.RoleType;
import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.models.UserRole;
import com.sjarno.norascoffeeshop.repositories.UserAccountRepository;
import com.sjarno.norascoffeeshop.repositories.UserRoleRepository;

import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;


    public UserAccount createNewEmployee(UserAccount userAccount) {
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        UserRole userRole = this.userRoleService.findByRoleType(RoleType.ROLE_EMPLOYEE);
        userAccount.setRoles(
            new ArrayList<>(Arrays.asList(userRole))
        );
        
        return this.userAccountRepository.save(userAccount);
    }
    
    
}
