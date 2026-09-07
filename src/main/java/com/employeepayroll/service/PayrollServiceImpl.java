package com.employeepayroll.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.employeepayroll.dao.PayrollDAO;
import com.employeepayroll.dao.PayrollDAOImpl;
import com.employeepayroll.model.Payroll;

public class PayrollServiceImpl implements PayrollService {

    private final PayrollDAO payrollDAO;

    public PayrollServiceImpl() {
        this.payrollDAO = new PayrollDAOImpl();
    }

    public PayrollServiceImpl(PayrollDAO payrollDAO) {
        this.payrollDAO = payrollDAO;
    }

    // --------------------------generatePayroll---------------------------------------------------

    @Override
    public void generatePayroll(Payroll payroll) throws SQLException {

        validatePayroll(payroll);

        BigDecimal grossSalary = payroll.getBasicSalary()
                .add(payroll.getAllowance());

        BigDecimal netSalary = grossSalary
                .subtract(payroll.getDeduction());

        payroll.setGrossSalary(grossSalary);
        payroll.setNetSalary(netSalary);

        if (payroll.getPaymentStatus() == null ||
                payroll.getPaymentStatus().isBlank()) {

            payroll.setPaymentStatus("PENDING");
        }

        payrollDAO.addPayroll(payroll);
    }

    // --------------------------getPayrollById---------------------------------------------------

    @Override
    public Payroll getPayrollById(int payrollId) throws SQLException {

        if (payrollId <= 0) {
            throw new IllegalArgumentException(
                    "Payroll ID must be greater than 0"
            );
        }

        return payrollDAO.getPayrollById(payrollId);
    }

    // --------------------------getAllPayrolls---------------------------------------------------

    @Override
    public List<Payroll> getAllPayrolls() throws SQLException {
        return payrollDAO.getAllPayrolls();
    }

    // --------------------------getPayrollsByEmployeeId---------------------------------------------------

    @Override
    public List<Payroll> getPayrollsByEmployeeId(int employeeId)
            throws SQLException {

        if (employeeId <= 0) {
            throw new IllegalArgumentException(
                    "Employee ID must be greater than 0"
            );
        }

        return payrollDAO.getPayrollsByEmployeeId(employeeId);
    }

    // --------------------------updatePayroll---------------------------------------------------

    @Override
    public boolean updatePayroll(Payroll payroll) throws SQLException {

        validatePayroll(payroll);

        if (payroll.getPayrollId() <= 0) {
            throw new IllegalArgumentException(
                    "Payroll ID must be greater than 0"
            );
        }

        BigDecimal grossSalary = payroll.getBasicSalary()
                .add(payroll.getAllowance());

        BigDecimal netSalary = grossSalary
                .subtract(payroll.getDeduction());

        payroll.setGrossSalary(grossSalary);
        payroll.setNetSalary(netSalary);

        return payrollDAO.updatePayroll(payroll);
    }

    // --------------------------deletePayroll---------------------------------------------------

    @Override
    public boolean deletePayroll(int payrollId) throws SQLException {

        if (payrollId <= 0) {
            throw new IllegalArgumentException(
                    "Payroll ID must be greater than 0"
            );
        }

        return payrollDAO.deletePayroll(payrollId);
    }

    // --------------------------validatePayroll---------------------------------------------------

    private void validatePayroll(Payroll payroll) {

        if (payroll == null) {
            throw new IllegalArgumentException(
                    "Payroll cannot be null"
            );
        }

        if (payroll.getEmployeeId() <= 0) {
            throw new IllegalArgumentException(
                    "Employee ID must be greater than 0"
            );
        }

        if (payroll.getSalaryMonth() == null) {
            throw new IllegalArgumentException(
                    "Salary month cannot be null"
            );
        }

        if (payroll.getBasicSalary() == null ||
                payroll.getBasicSalary().compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Basic salary cannot be null or negative"
            );
        }

        if (payroll.getAllowance() == null ||
                payroll.getAllowance().compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Allowance cannot be null or negative"
            );
        }

        if (payroll.getDeduction() == null ||
                payroll.getDeduction().compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Deduction cannot be null or negative"
            );
        }

        if (payroll.getDeduction()
                .compareTo(
                        payroll.getBasicSalary()
                                .add(payroll.getAllowance())
                ) > 0) {

            throw new IllegalArgumentException(
                    "Deduction cannot be greater than gross salary"
            );
        }
    }
}
