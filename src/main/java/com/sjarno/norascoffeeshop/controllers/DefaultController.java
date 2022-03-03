package com.sjarno.norascoffeeshop.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.sjarno.norascoffeeshop.services.UserAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @Autowired
    private UserAccountService userService;

    /* Quick testing for server: */
    @GetMapping("/greet")
    public Map<String, String> serverGreeting() {
        Map<String, String> greeting = new HashMap<>();
        greeting.put(
            "greet", 
            "Hi, and welcome! The site is currently under construction, but stay tuned for upcoming site.");
        return greeting;
        
    }
    /* @PostConstruct
    public void init() {
        try {
            userService.createUserAdmin();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Virhe juu: "+e.getMessage());
            System.out.println();
        }
        
    } */
    
}
