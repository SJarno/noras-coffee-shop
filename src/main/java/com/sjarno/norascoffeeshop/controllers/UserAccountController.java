package com.sjarno.norascoffeeshop.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.repositories.UserAccountRepository;
import com.sjarno.norascoffeeshop.security.SecurityContextService;
import com.sjarno.norascoffeeshop.services.UserAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private SecurityContextService securityContextService;

    /* Update username */
   /*  @Transactional */
    @PutMapping("/update-username")
    public ResponseEntity<?> updateUsername(
            @RequestBody String newUsername,
            HttpServletRequest request) {
        
        Map<String, String> result = new HashMap<>();
        try {
            UserAccount user = userAccountService.updateUsername(newUsername.trim());
            result.put("username", user.getUsername());
            return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }
    /* Update password */
    @PutMapping("/update-password")
    public void updatePassword(
        @RequestBody String oldPassword,
        @RequestBody String newPassword
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserAccount> userAccount = this.userAccountRepository.findByUsername(auth.getName());
        if (passwordEncoder.matches(oldPassword, userAccount.get().getPassword())) {
            userAccount.get().setPassword(passwordEncoder.encode(newPassword));
        }
        
    }

}
