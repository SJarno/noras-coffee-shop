package com.sjarno.norascoffeeshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.management.relation.Role;

import com.sjarno.norascoffeeshop.models.RoleType;
import com.sjarno.norascoffeeshop.models.UserRole;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRoleServiceTest {

    @Autowired
    private UserRoleService userRoleService;

    @Test
    void testAddBasicRoleTypes() {
        List<UserRole> roles = this.userRoleService.getAllUserRoleTypes();
        assertEquals(3, roles.size());
        
    }

    @Test
    void testFindByRoleType() {
        UserRole adminType = this.userRoleService.findByRoleType(RoleType.ROLE_ADMIN);
        UserRole employeeType = this.userRoleService.findByRoleType(RoleType.ROLE_EMPLOYEE);
        UserRole customerType = this.userRoleService.findByRoleType(RoleType.ROLE_CUSTOMER);
        
        assertEquals(RoleType.ROLE_ADMIN, adminType.getRoleType());
        assertEquals(RoleType.ROLE_EMPLOYEE, employeeType.getRoleType());
        assertEquals(RoleType.ROLE_CUSTOMER, customerType.getRoleType());
    }

    @Test
    void testGetAllUserRoleTypes() {
        List<UserRole> roles = this.userRoleService.getAllUserRoleTypes();
        assertEquals(3, roles.size());
        assertEquals(RoleType.ROLE_ADMIN, roles.get(0).getRoleType());
        assertEquals(RoleType.ROLE_EMPLOYEE, roles.get(1).getRoleType());
        assertEquals(RoleType.ROLE_CUSTOMER, roles.get(2).getRoleType());
    }

}
