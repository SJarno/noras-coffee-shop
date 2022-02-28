package com.sjarno.norascoffeeshop.controllers;

import java.util.Optional;

import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.repositories.UserAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccountController {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Transactional
    @PutMapping("/update-username/{oldUsername}/{newUsername}")
    public String updateUsername(
        @PathVariable String oldUsername,
        @PathVariable String newUsername 
        ) {
        System.out.println();
        System.out.println("Old username:");
        System.out.println(oldUsername);
        System.out.println("New username:");
        System.out.println(newUsername);
        System.out.println();
        //Optional<UserAccount> userAccount = this.userAccountRepository.findByUsername(oldUsername);
        return "result: Juu";
        
    }
    
}
