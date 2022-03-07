package com.sjarno.norascoffeeshop.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sjarno.norascoffeeshop.models.RoleType;
import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserRoleService userRoleService;

    public UserAccount createNewEmployee(UserAccount userAccount)
            throws IllegalArgumentException {
        UserRole userRole = this.userRoleService.findByRoleType(RoleType.ROLE_EMPLOYEE);
        userAccount.setRoles(
                new ArrayList<>(Arrays.asList(userRole)));

        return this.userAccountService.saveUserAccount(userAccount);
    }
    /* public List<UserAccount> finAllEmployees() {
        UserRole userRole = new UserRole();
        userRole.setRoleType(RoleType.ROLE_EMPLOYEE);
        return this.userAccountService.findUserByUserRole(userRole);
    } */

}
