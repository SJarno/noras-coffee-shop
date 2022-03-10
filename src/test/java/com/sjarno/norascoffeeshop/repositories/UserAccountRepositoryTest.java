package com.sjarno.norascoffeeshop.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sjarno.norascoffeeshop.models.RoleType;
import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.models.UserRole;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    UserAccount userAdmin;
    UserAccount userEmployee;
    UserAccount userCustomer;
    UserAccount userCustomerEmployee;

    UserAccount notSavedAccount;

    @BeforeEach
    void setUp() {
        List<UserRole> userRoles = this.createDefaultRoles();
        
        this.userAdmin = this.createNewUser(
            "user", "pass", new ArrayList<>(Arrays.asList(userRoles.get(0))));
        this.userEmployee = this.createNewUser(
            "nameEmp", "password", new ArrayList<>(Arrays.asList(userRoles.get(1))));
        this.userCustomer = this.createNewUser(
            "nameCustomer", "password", new ArrayList<>(Arrays.asList(userRoles.get(2))));
        this.userCustomerEmployee = this.createNewUser(
            "username", "password", new ArrayList<>(Arrays.asList(
                userRoles.get(1), userRoles.get(2)
            )));
        
        notSavedAccount = new UserAccount();
        notSavedAccount.setUsername("user");
        notSavedAccount.setPassword("pass");
        notSavedAccount.setRoles(Arrays.asList(userRoles.get(0)));

        

    }

    @Test
    void testFindByUsername() {
        UserAccount user = this.userAccountRepository.findByUsername("user").get();
        assertEquals(4, this.userAccountRepository.findAll().size());
        assertEquals(userAdmin, user);
        assertEquals(4, this.userAccountRepository.findByUsername("user").get().getId());
        assertEquals(RoleType.ROLE_ADMIN, user.getRoles().get(0).getRoleType());
        
    }
    @Test
    void findUsersByRole() {
        UserRole adminRole = this.userRoleRepository.findAll().get(0);
        List<UserAccount> admins = this.userAccountRepository
            .findByRolesContaining(adminRole);
        assertEquals(1, admins.size());
        assertEquals("user", admins.get(0).getUsername());
        assertEquals(RoleType.ROLE_ADMIN, admins.get(0).getRoles().get(0).getRoleType());

        UserRole empRole = this.userRoleRepository.findAll().get(1);
        List<UserAccount> employees = this.userAccountRepository
            .findByRolesContaining(empRole);
        assertEquals(2, employees.size());
        assertEquals("nameEmp", employees.get(0).getUsername());
        assertEquals(RoleType.ROLE_EMPLOYEE, employees.get(0).getRoles().get(0).getRoleType());

        UserRole custRole = this.userRoleRepository.findAll().get(2);
        List<UserAccount> customers = this.userAccountRepository.findByRolesContaining(custRole);
        assertEquals(2, customers.size());
        assertEquals("nameCustomer", customers.get(0).getUsername());
        assertEquals(RoleType.ROLE_CUSTOMER, customers.get(0).getRoles().get(0).getRoleType());
    }

    @Test
    void findUserByIdAndUserRole() {
        UserRole adminRole = this.userRoleRepository.findAll().get(0);
        UserAccount adminAccount = this.userAccountRepository.findByIdAndRolesContaining(4L, adminRole).get();
        assertEquals("user", adminAccount.getUsername());
        assertEquals(4, adminAccount.getId());
        assertEquals(RoleType.ROLE_ADMIN, adminAccount.getRoles().get(0).getRoleType());

        UserRole employeeRole = this.userRoleRepository.findAll().get(1);
        UserAccount employeeAccount = this.userAccountRepository
            .findByIdAndRolesContaining(5L, employeeRole).get();
        assertEquals("nameEmp", employeeAccount.getUsername());
        assertEquals(5, employeeAccount.getId());
        assertEquals(RoleType.ROLE_EMPLOYEE, employeeAccount.getRoles().get(0).getRoleType());

        UserRole customerRole = this.userRoleRepository.findAll().get(2);
        UserAccount customerAccount = this.userAccountRepository
            .findByIdAndRolesContaining(6L, customerRole).get();
        assertEquals("nameCustomer", customerAccount.getUsername());
        assertEquals(6, customerAccount.getId());
        assertEquals(RoleType.ROLE_CUSTOMER, customerAccount.getRoles().get(0).getRoleType());

        UserAccount customerAndEmployeeByEmpRole = this.userAccountRepository
            .findByIdAndRolesContaining(7L, employeeRole).get();
        assertEquals(7, customerAndEmployeeByEmpRole.getId());
        assertEquals(RoleType.ROLE_EMPLOYEE, customerAndEmployeeByEmpRole.getRoles().get(0).getRoleType());
        UserAccount customerAndEmployeeByCustomerRole = this.userAccountRepository
            .findByIdAndRolesContaining(7L, customerRole).get();
        assertEquals(7, customerAndEmployeeByCustomerRole.getId());
        assertEquals(RoleType.ROLE_CUSTOMER, customerAndEmployeeByCustomerRole.getRoles().get(1).getRoleType());
        assertEquals(customerAndEmployeeByEmpRole, customerAndEmployeeByCustomerRole);
        
    }
    @Test
    void findByUsernameAndRole() {
        UserRole adminRole = this.userRoleRepository.findAll().get(0);
        UserAccount adminAccount = this.userAccountRepository
            .findByUsernameAndRolesContaining("user", adminRole).get();
        assertEquals("user", adminAccount.getUsername());
        assertEquals(this.userAdmin, adminAccount);

    }

    @Test
    void multipleRolesReturnsCorrectAmount() {
        List<UserRole> roles = this.userRoleRepository.findAll();
        createNewUser("empCustomer", "password", new ArrayList<>(Arrays.asList(roles.get(1), roles.get(2))));
        List<UserAccount> usersCustomersEmployees = this.userAccountRepository
            .findByRolesContaining(roles.get(1));
        assertEquals(3, usersCustomersEmployees.size());
        assertEquals(2, usersCustomersEmployees.get(1).getRoles().size());
        assertEquals(RoleType.ROLE_EMPLOYEE, usersCustomersEmployees.get(1).getRoles().get(0).getRoleType());
        assertEquals(RoleType.ROLE_CUSTOMER, usersCustomersEmployees.get(1).getRoles().get(1).getRoleType());
    }

    private UserAccount createNewUser(String username, String password, ArrayList<UserRole> roles) {
        return this.userAccountRepository.save(
            new UserAccount(
                username, 
                password, 
                roles)
            );
    }
    private List<UserRole> createDefaultRoles() {
        UserRole adminRole = new UserRole(RoleType.ROLE_ADMIN, new ArrayList<>());
        UserRole employeeRole = new UserRole(RoleType.ROLE_EMPLOYEE, new ArrayList<>());
        UserRole customerRole = new UserRole(RoleType.ROLE_CUSTOMER, new ArrayList<>());
        return this.userRoleRepository.saveAll(new ArrayList<>(Arrays.asList(adminRole, employeeRole, customerRole)));
    }
}
