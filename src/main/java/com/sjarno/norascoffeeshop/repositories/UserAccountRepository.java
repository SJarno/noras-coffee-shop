package com.sjarno.norascoffeeshop.repositories;

import com.sjarno.norascoffeeshop.models.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long>{
    
}
