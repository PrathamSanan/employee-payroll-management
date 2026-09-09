package com.employeepayroll.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.employeepayroll.model.Employee;

class EmployeeServiceImplTest {

    private EmployeeService createService() {
        return new EmployeeServiceImpl();
    }

    private Employee createValidEmployee() {

        Employee employee = new Employee();

        employee.setFirstName("Test");
        employee.setLastName("Employee");
        employee.setEmail(
                "test" + System.currentTimeMillis() + "@example.com"
        );
        employee.setPhone(
        "9" + String.valueOf(System.currentTimeMillis()).substring(4)
);
        employee.setDepartmentId(28);
        employee.setDesignation("Software Engineer");
        employee.setJoiningDate(LocalDate.now().minusDays(30));
        employee.setBasicSalary(new BigDecimal("50000"));
        employee.setStatus("ACTIVE");

        return employee;
    }

    // --------------------------addEmployee---------------------------------------------------

    @Test
    void testAddValidEmployee() {

        Employee employee = createValidEmployee();

        EmployeeService service = createService();

        assertDoesNotThrow(
                () -> service.addEmployee(employee)
        );
    }

    @Test
    void testAddNullEmployee() {

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addEmployee(null)
        );
    }

    @Test
    void testBlankFirstName() {

        Employee employee = createValidEmployee();
        employee.setFirstName("");

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addEmployee(employee)
        );
    }

    @Test
    void testBlankLastName() {

        Employee employee = createValidEmployee();
        employee.setLastName("");

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addEmployee(employee)
        );
    }

    @Test
    void testInvalidEmail() {

        Employee employee = createValidEmployee();
        employee.setEmail("invalid-email");

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addEmployee(employee)
        );
    }

    @Test
    void testInvalidDepartmentId() {

        Employee employee = createValidEmployee();
        employee.setDepartmentId(0);

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addEmployee(employee)
        );
    }

    @Test
    void testBlankDesignation() {

        Employee employee = createValidEmployee();
        employee.setDesignation("");

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addEmployee(employee)
        );
    }

    @Test
    void testNullJoiningDate() {

        Employee employee = createValidEmployee();
        employee.setJoiningDate(null);

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addEmployee(employee)
        );
    }

    @Test
    void testFutureJoiningDate() {

        Employee employee = createValidEmployee();
        employee.setJoiningDate(LocalDate.now().plusDays(1));

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addEmployee(employee)
        );
    }

    @Test
    void testInvalidSalary() {

        Employee employee = createValidEmployee();
        employee.setBasicSalary(new BigDecimal("-1000"));

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addEmployee(employee)
        );
    }

    @Test
    void testNullSalary() {

        Employee employee = createValidEmployee();
        employee.setBasicSalary(null);

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addEmployee(employee)
        );
    }

    @Test
    void testInvalidStatus() {

        Employee employee = createValidEmployee();
        employee.setStatus("INVALID");

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addEmployee(employee)
        );
    }

    // --------------------------getEmployeeById---------------------------------------------------

    @Test
    void testGetEmployeeWithInvalidId() {

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getEmployeeById(0)
        );
    }

    @Test
    void testGetEmployeeWithNegativeId() {

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getEmployeeById(-1)
        );
    }

    // --------------------------searchEmployees---------------------------------------------------

    @Test
    void testSearchWithValidKeyword() {

        EmployeeService service = createService();

        assertDoesNotThrow(
                () -> service.searchEmployees("Test")
        );
    }

    @Test
    void testSearchWithNullKeyword() {

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.searchEmployees(null)
        );
    }

    @Test
    void testSearchWithBlankKeyword() {

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.searchEmployees("")
        );
    }

    // --------------------------updateEmployee---------------------------------------------------

    @Test
    void testUpdateWithInvalidId() {

        Employee employee = createValidEmployee();
        employee.setEmployeeId(0);

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.updateEmployee(employee)
        );
    }

    @Test
    void testUpdateNullEmployee() {

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.updateEmployee(null)
        );
    }

    // --------------------------deleteEmployee---------------------------------------------------

    @Test
    void testDeleteWithInvalidId() {

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.deleteEmployee(0)
        );
    }

    @Test
    void testDeleteWithNegativeId() {

        EmployeeService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.deleteEmployee(-1)
        );
    }
}
