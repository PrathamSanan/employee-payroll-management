package com.employeepayroll.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {

    private int attendanceId;
    private int employeeId;
    private LocalDate attendanceDate;
    private String status;
    private LocalTime checkIn;
    private LocalTime checkOut;

    public Attendance() {
    }

    public Attendance(
            int attendanceId,
            int employeeId,
            LocalDate attendanceDate,
            String status,
            LocalTime checkIn,
            LocalTime checkOut) {

        this.attendanceId = attendanceId;
        this.employeeId = employeeId;
        this.attendanceDate = attendanceDate;
        this.status = status;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalTime checkOut) {
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "attendanceId=" + attendanceId +
                ", employeeId=" + employeeId +
                ", attendanceDate=" + attendanceDate +
                ", status='" + status + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                '}';
    }
}