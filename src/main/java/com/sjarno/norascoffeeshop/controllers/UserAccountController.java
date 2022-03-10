package com.sjarno.norascoffeeshop.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.services.UserAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    /* Update username */
    @PutMapping("/update-username")
    public ResponseEntity<?> updateUsername(@RequestBody String newUsername) {
        Map<String, String> result = new HashMap<>();
        try {
            UserAccount user = userAccountService.updateUsername(newUsername);
            result.put("username", user.getUsername());
            return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    /* Update password */
    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(
            @RequestParam String newPassword,
            @RequestParam String oldPassword) {
        Map<String, UserAccount> result = new HashMap<>();
        try {
            UserAccount updatedAccount = userAccountService.updatePassword(newPassword, oldPassword);
            result.put("user", updatedAccount);
            return new ResponseEntity<Map<String, UserAccount>>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

}
