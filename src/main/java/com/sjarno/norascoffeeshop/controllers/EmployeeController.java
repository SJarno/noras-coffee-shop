package com.sjarno.norascoffeeshop.controllers;

import java.util.ArrayList;

import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.services.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<?> createNewEmployee(@RequestBody UserAccount newEmployee) {
        try {
            return new ResponseEntity<UserAccount>(
                    this.employeeService.createNewEmployee(newEmployee),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

}
