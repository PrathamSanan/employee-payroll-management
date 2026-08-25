-- ============================================
-- Employee & Payroll Management System
-- Database Schema
-- ============================================

-- Create database
CREATE DATABASE IF NOT EXISTS employee_payroll_db;

-- Select database
USE employee_payroll_db;


-- ============================================
-- 1. DEPARTMENTS
-- ============================================

CREATE TABLE IF NOT EXISTS departments (
    department_id INT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- ============================================
-- 2. EMPLOYEES
-- ============================================

CREATE TABLE IF NOT EXISTS employees (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) UNIQUE,
    department_id INT NOT NULL,
    designation VARCHAR(100) NOT NULL,
    joining_date DATE NOT NULL,
    basic_salary DECIMAL(10,2) NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_employee_department
        FOREIGN KEY (department_id)
        REFERENCES departments(department_id),

    CONSTRAINT chk_employee_salary
        CHECK (basic_salary >= 0)
);


-- ============================================
-- 3. USERS
-- ============================================

CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'EMPLOYEE') NOT NULL,
    employee_id INT UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_employee
        FOREIGN KEY (employee_id)
        REFERENCES employees(employee_id)
);


-- ============================================
-- 4. ATTENDANCE
-- ============================================

CREATE TABLE IF NOT EXISTS attendance (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    attendance_date DATE NOT NULL,
    status ENUM('PRESENT', 'ABSENT', 'LEAVE') NOT NULL,
    check_in TIME,
    check_out TIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_attendance_employee
        FOREIGN KEY (employee_id)
        REFERENCES employees(employee_id),

    CONSTRAINT uq_employee_attendance_date
        UNIQUE (employee_id, attendance_date)
);


-- ============================================
-- 5. PAYROLL
-- ============================================

CREATE TABLE IF NOT EXISTS payroll (
    payroll_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    salary_month DATE NOT NULL,
    basic_salary DECIMAL(10,2) NOT NULL,
    allowance DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    deduction DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    gross_salary DECIMAL(10,2) NOT NULL,
    net_salary DECIMAL(10,2) NOT NULL,
    payment_status ENUM('PENDING', 'PAID') NOT NULL DEFAULT 'PENDING',
    generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_payroll_employee
        FOREIGN KEY (employee_id)
        REFERENCES employees(employee_id),

    CONSTRAINT uq_employee_salary_month
        UNIQUE (employee_id, salary_month),

    CONSTRAINT chk_payroll_basic_salary
        CHECK (basic_salary >= 0),

    CONSTRAINT chk_payroll_allowance
        CHECK (allowance >= 0),

    CONSTRAINT chk_payroll_deduction
        CHECK (deduction >= 0),

    CONSTRAINT chk_payroll_gross_salary
        CHECK (gross_salary >= 0),

    CONSTRAINT chk_payroll_net_salary
        CHECK (net_salary >= 0)
);