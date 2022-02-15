package com.sjarno.norascoffeeshop.repositories;

import java.util.Optional;

import com.sjarno.norascoffeeshop.models.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long>{
    Optional<UserAccount> findByUsername(String username);
}
