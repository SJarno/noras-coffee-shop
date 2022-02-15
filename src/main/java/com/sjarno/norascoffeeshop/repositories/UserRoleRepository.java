package com.sjarno.norascoffeeshop.repositories;

import com.sjarno.norascoffeeshop.models.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
    
}
