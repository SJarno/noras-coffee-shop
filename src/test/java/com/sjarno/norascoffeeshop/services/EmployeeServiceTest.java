package com.sjarno.norascoffeeshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import com.sjarno.norascoffeeshop.models.RoleType;
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
    private UserAccount dummyEmpAccount;

    @BeforeEach
    void setUp() {
        this.employeeAccount = new UserAccount("Minna", "passu", new ArrayList<>());
        this.dummyEmpAccount = new UserAccount();
    }

    @Test
    void testCreateNewEmployee() {
        assertEquals(1, this.userAccountRepository.findAll().size());
        this.employeeService.createNewEmployee(employeeAccount);
        assertEquals(2, this.userAccountRepository.findAll().size());
        UserAccount userAccount = this.userAccountRepository.findByUsername("Minna").get();
        assertEquals("Minna", userAccount.getUsername());
        assertEquals(1, userAccount.getRoles().size());
        assertEquals("ROLE_EMPLOYEE", userAccount.getRoles().get(0).getRoleType().toString());
    }
    @Test
    void addingSameUsernameThrowsException() {
        dummyEmpAccount.setUsername("Minna");
        dummyEmpAccount.setPassword("password");
        Exception usernameException = assertThrows(Exception.class, () -> {
            this.employeeService.createNewEmployee(employeeAccount);
            this.employeeService.createNewEmployee(dummyEmpAccount);
        });
        assertEquals("Username taken.", usernameException.getMessage());

    }
    @Test
    void incorrectInputValuesThrowsException() {
        this.dummyEmpAccount.setUsername("use");
        this.dummyEmpAccount.setPassword("password");
        Exception incorrectUsernameLengthException = assertThrows(Exception.class, () -> {
            this.employeeService.createNewEmployee(this.dummyEmpAccount);
        });
        assertEquals("Username must be between 4 and 40 characters", incorrectUsernameLengthException.getMessage());

        this.dummyEmpAccount.setUsername("Mikko");
        this.dummyEmpAccount.setPassword("pas");
        Exception incorrectPasswordLengthException = assertThrows(Exception.class, () -> {
            this.employeeService.createNewEmployee(this.dummyEmpAccount);
        });
        assertEquals("Password length violation", incorrectPasswordLengthException.getMessage());

        this.dummyEmpAccount.setUsername("");
        this.dummyEmpAccount.setPassword("password");
        Exception usernameEmptyException = assertThrows(Exception.class, () -> {
            this.employeeService.createNewEmployee(this.dummyEmpAccount);
        });
        assertEquals("Username cannot be empty", usernameEmptyException.getMessage());

        this.dummyEmpAccount.setUsername("Mikko");
        this.dummyEmpAccount.setPassword("");
        Exception passwordEmptyException = assertThrows(Exception.class, () -> {
            this.employeeService.createNewEmployee(this.dummyEmpAccount);
        });
        assertEquals("Password cannot be empty", passwordEmptyException.getMessage());

        UserAccount nullValueUser = new UserAccount();

        Exception nullValueExcpetion = assertThrows(Exception.class, () -> {
            this.employeeService.createNewEmployee(nullValueUser);
        });
        assertEquals("Username cannot be null", nullValueExcpetion.getMessage());

        Exception nullPasswordException = assertThrows(Exception.class, () -> {
            nullValueUser.setUsername("Mikko");
            this.employeeService.createNewEmployee(nullValueUser);
        });
        assertEquals("Password cannot be null", nullPasswordException.getMessage());
        
    }
    @Test
    void findAllEmployees() {
        this.employeeService.createNewEmployee(this.employeeAccount);
        assertEquals(1, this.employeeService.finAllEmployees().size());
    }

    @Test
    void findEmployeeById() {
        this.employeeService.createNewEmployee(this.employeeAccount);
        UserAccount empAccount = this.employeeService.getEmployeeById(5L);
        assertEquals(5, empAccount.getId());
        assertEquals("Minna", empAccount.getUsername());
        assertEquals(RoleType.ROLE_EMPLOYEE, empAccount.getRoles().get(0).getRoleType());
    }

    @Test
    void findEmployeeByIdThrowsException() {
        this.employeeService.createNewEmployee(this.employeeAccount);
        Exception exception = assertThrows(Exception.class, () -> {
            this.employeeService.getEmployeeById(9L);
        });
        assertEquals("User not found", exception.getMessage());
        
        
    }
}
