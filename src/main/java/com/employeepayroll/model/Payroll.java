package com.employeepayroll.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

public class Payroll {

    private int payrollId;
    private int employeeId;
    private LocalDate salaryMonth;
    private BigDecimal basicSalary;
    private BigDecimal allowance;
    private BigDecimal deduction;
    private BigDecimal grossSalary;
    private BigDecimal netSalary;
    private String paymentStatus;
    private Timestamp generatedAt;

    public Payroll() {
    }

    public Payroll(
            int payrollId,
            int employeeId,
            LocalDate salaryMonth,
            BigDecimal basicSalary,
            BigDecimal allowance,
            BigDecimal deduction,
            BigDecimal grossSalary,
            BigDecimal netSalary,
            String paymentStatus,
            Timestamp generatedAt) {

        this.payrollId = payrollId;
        this.employeeId = employeeId;
        this.salaryMonth = salaryMonth;
        this.basicSalary = basicSalary;
        this.allowance = allowance;
        this.deduction = deduction;
        this.grossSalary = grossSalary;
        this.netSalary = netSalary;
        this.paymentStatus = paymentStatus;
        this.generatedAt = generatedAt;
    }

    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(LocalDate salaryMonth) {
        this.salaryMonth = salaryMonth;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public BigDecimal getAllowance() {
        return allowance;
    }

    public void setAllowance(BigDecimal allowance) {
        this.allowance = allowance;
    }

    public BigDecimal getDeduction() {
        return deduction;
    }

    public void setDeduction(BigDecimal deduction) {
        this.deduction = deduction;
    }

    public BigDecimal getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(BigDecimal grossSalary) {
        this.grossSalary = grossSalary;
    }

    public BigDecimal getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(BigDecimal netSalary) {
        this.netSalary = netSalary;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Timestamp getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Timestamp generatedAt) {
        this.generatedAt = generatedAt;
    }

    @Override
    public String toString() {
        return "Payroll{" +
                "payrollId=" + payrollId +
                ", employeeId=" + employeeId +
                ", salaryMonth=" + salaryMonth +
                ", basicSalary=" + basicSalary +
                ", allowance=" + allowance +
                ", deduction=" + deduction +
                ", grossSalary=" + grossSalary +
                ", netSalary=" + netSalary +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", generatedAt=" + generatedAt +
                '}';
    }
}