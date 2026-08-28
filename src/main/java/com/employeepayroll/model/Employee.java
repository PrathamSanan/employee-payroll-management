package com.employeepayroll.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {

    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int departmentId;
    private String designation;
    private LocalDate joiningDate;
    private BigDecimal basicSalary;
    private String status;

    public Employee() {
    }

    public Employee(
            int employeeId,
            String firstName,
            String lastName,
            String email,
            String phone,
            int departmentId,
            String designation,
            LocalDate joiningDate,
            BigDecimal basicSalary,
            String status) {

        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.departmentId = departmentId;
        this.designation = designation;
        this.joiningDate = joiningDate;
        this.basicSalary = basicSalary;
        this.status = status;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", departmentId=" + departmentId +
                ", designation='" + designation + '\'' +
                ", joiningDate=" + joiningDate +
                ", basicSalary=" + basicSalary +
                ", status='" + status + '\'' +
                '}';
    }
}