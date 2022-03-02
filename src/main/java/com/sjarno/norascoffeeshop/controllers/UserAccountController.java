package com.sjarno.norascoffeeshop.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.repositories.UserAccountRepository;
import com.sjarno.norascoffeeshop.security.SecurityContextService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private SecurityContextService securityContextService;

    @Transactional
    // @PutMapping("/update-username/{oldUsername}/{newUsername}")
    @PutMapping("/update-username")
    public Map<String, String> updateUsername(
            @RequestBody String newUsername,
            HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Details: ");
        System.out.println(auth.getDetails());
        String oldUsername = auth.getName();
        System.out.println("Autentikoitu: " + auth.isAuthenticated());
        Optional<UserAccount> userAccount = this.userAccountRepository.findByUsername(oldUsername);
        Map<String, String> testmap = new HashMap<>();

        if (userAccount.isPresent());
        userAccount.get().setUsername(newUsername);
        //securityContextService.refreshAuth(newUsername, userAccount.get().getPassword(), request);

        testmap.put("username", newUsername);
        return testmap;

    

    }

}
