package com.sjarno.norascoffeeshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import com.sjarno.norascoffeeshop.models.UserAccount;
import com.sjarno.norascoffeeshop.repositories.UserAccountRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserAccountRepository userAccountRepository;

    private UserAccount employeeAccount;

    @BeforeEach
    void setUp() {
        this.employeeAccount = new UserAccount("Minna", "passu", new ArrayList<>());
    }

    @Test
    void testCreateNewEmployee() {
        assertEquals(1, this.userAccountRepository.findAll().size());
        this.employeeService.createNewEmployee(employeeAccount);
        assertEquals(2, this.userAccountRepository.findAll().size());
    }
    @Test
    void addingSameUsernameThrowsException() {

    }
    @Test
    void incorrectInputValuesThrowsException() {

    }
}
