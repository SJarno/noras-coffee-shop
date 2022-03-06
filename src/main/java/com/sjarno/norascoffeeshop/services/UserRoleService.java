package com.sjarno.norascoffeeshop.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sjarno.norascoffeeshop.models.RoleType;
import com.sjarno.norascoffeeshop.models.UserRole;
import com.sjarno.norascoffeeshop.repositories.UserRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public void addBasicRoleTypes() {
        UserRole adminRole = new UserRole();
        adminRole.setRoleType(RoleType.ROLE_ADMIN);
        adminRole.setUsers(new ArrayList<>());
        UserRole employeeRole = new UserRole();
        employeeRole.setRoleType(RoleType.ROLE_EMPLOYEE);
        employeeRole.setUsers(new ArrayList<>());
        try {
            checkIfRoleTypeExists(adminRole);
            checkIfRoleTypeExists(employeeRole);
        } catch (Exception e) {
            System.out.println();
            System.out.println("Virhe luodessa rooleja");
            System.out.println(e.getMessage());
        }

    }
    public List<UserRole> getAllUserRoleTypes() {
        return this.userRoleRepository.findAll();
    }
    public UserRole findByRoleType(RoleType roleType) {
        Optional<UserRole> role = this.userRoleRepository.findByRoleType(roleType);
        if (role.isPresent()) {
            return role.get();
        }
        throw new IllegalArgumentException("Roletype not found");
    }

    public void checkIfRoleTypeExists(UserRole userRole) {
        if (this.userRoleRepository.findByRoleType(userRole.getRoleType()).isPresent()) {
            throw new IllegalArgumentException("Role exists");
        }
        this.userRoleRepository.save(userRole);
    }

}
