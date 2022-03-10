package com.sjarno.norascoffeeshop.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sjarno.norascoffeeshop.models.RoleType;
import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserRoleService userRoleService;

    public UserRole getEmployeeRole() {
        return this.userRoleService.findByRoleType(RoleType.ROLE_EMPLOYEE);
    }

    /* Create new employee */
    public UserAccount createNewEmployee(UserAccount userAccount)
            throws IllegalArgumentException {
        userAccount.getRoles().add(getEmployeeRole());

        return this.userAccountService.saveUserAccount(userAccount);
    }

    /* Get all emplyees */
    public List<UserAccount> finAllEmployees() {
        return this.userAccountService.findUsersByUserRole(getEmployeeRole());
    }

    /* Get employee by id */
    public UserAccount getEmployeeById(Long id) {
        return this.userAccountService.getUserByIdAndRole(getEmployeeRole(), id);
    }

    /* Get employee by username */
    public UserAccount getEmployeeByUsername(String username) {
        return this.userAccountService.getUserByUsernameAndRole(username, getEmployeeRole());
    }

    /* Update employee */

    /* Delete employee */

}
