package com.employeepayroll.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.employeepayroll.model.Department;

class DepartmentServiceImplTest {

    private DepartmentService createService() {
        return new DepartmentServiceImpl();
    }

    @Test
    void testAddValidDepartment() {

        Department department = new Department();
        department.setDepartmentName("Engineering");

        DepartmentService service = createService();

        assertDoesNotThrow(
                () -> service.addDepartment(department)
        );
    }

    @Test
    void testNullDepartmentValidation() {

        DepartmentService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addDepartment(null)
        );
    }

    @Test
    void testBlankDepartmentNameValidation() {

        Department department = new Department();
        department.setDepartmentName("");

        DepartmentService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addDepartment(department)
        );
    }

    @Test
    void testNullDepartmentNameValidation() {

        Department department = new Department();
        department.setDepartmentName(null);

        DepartmentService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addDepartment(department)
        );
    }

    @Test
    void testDepartmentNameExceedsMaximumLength() {

        Department department = new Department();
        department.setDepartmentName("A".repeat(101));

        DepartmentService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addDepartment(department)
        );
    }

    @Test
    void testGetDepartmentByInvalidId() {

        DepartmentService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getDepartmentById(0)
        );
    }

    @Test
    void testGetDepartmentByNegativeId() {

        DepartmentService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getDepartmentById(-1)
        );
    }

    @Test
    void testDeleteDepartmentWithInvalidId() {

        DepartmentService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.deleteDepartment(0)
        );
    }

    @Test
    void testUpdateDepartmentWithInvalidId() {

        Department department = new Department();

        department.setDepartmentId(0);
        department.setDepartmentName("Engineering");

        DepartmentService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.updateDepartment(department)
        );
    }

    @Test
    void testUpdateNullDepartment() {

        DepartmentService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.updateDepartment(null)
        );
    }

    @Test
    void testUpdateBlankDepartmentName() {

        Department department = new Department();

        department.setDepartmentId(1);
        department.setDepartmentName("");

        DepartmentService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.updateDepartment(department)
        );
    }

    @Test
    void testGetAllDepartments() {

        DepartmentService service = createService();

        assertDoesNotThrow(
                () -> service.getAllDepartments()
        );
    }
}