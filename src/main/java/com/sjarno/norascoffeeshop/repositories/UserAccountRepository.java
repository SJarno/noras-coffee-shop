package com.sjarno.norascoffeeshop.repositories;

import java.util.List;
import java.util.Optional;

import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.models.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long>{
    Optional<UserAccount> findByUsername(String username);
    List<UserAccount> findByRolesContaining(UserRole userRole);
    
}
