package com.sjarno.norascoffeeshop.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    /* Quick testing for server: */
    @GetMapping("/greet")
    public Map<String, String> serverGreeting() {
        Map<String, String> greeting = new HashMap<>();
        greeting.put(
            "greet", 
            "Hi, and welcome! The site is currently under construction, but stay tuned for upcoming site.");
        return greeting;
        
    }
    
}
