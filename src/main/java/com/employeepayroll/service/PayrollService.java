package com.employeepayroll.service;

import java.sql.SQLException;
import java.util.List;

import com.employeepayroll.model.Payroll;

public interface PayrollService {

    void generatePayroll(Payroll payroll) throws SQLException;

    Payroll getPayrollById(int payrollId) throws SQLException;

    List<Payroll> getAllPayrolls() throws SQLException;

    List<Payroll> getPayrollsByEmployeeId(int employeeId) throws SQLException;

    boolean updatePayroll(Payroll payroll) throws SQLException;

    boolean deletePayroll(int payrollId) throws SQLException;
}
