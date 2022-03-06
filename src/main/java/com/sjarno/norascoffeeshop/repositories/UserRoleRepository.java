package com.sjarno.norascoffeeshop.repositories;


import java.util.Optional;

import com.sjarno.norascoffeeshop.models.RoleType;
import com.sjarno.norascoffeeshop.models.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    // List<UserRole> findByRoleType(RoleType roleType);
    Optional<UserRole> findByRoleType(RoleType roleType);
}
