package com.employeepayroll.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.employeepayroll.dao.AttendanceDAO;
import com.employeepayroll.dao.AttendanceDAOImpl;
import com.employeepayroll.model.Attendance;

public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceDAO attendanceDAO;

    public AttendanceServiceImpl() {
        this.attendanceDAO = new AttendanceDAOImpl();
    }

    // --------------------------markAttendance---------------------------------------------------

    @Override
    public void addAttendance(Attendance attendance) {

        validateAttendance(attendance);

        attendanceDAO.addAttendance(attendance);
    }

    // --------------------------getAttendanceById---------------------------------------------------

    @Override
    public Attendance getAttendanceById(int attendanceId) {

        if (attendanceId <= 0) {
            throw new IllegalArgumentException(
                    "Attendance ID must be greater than 0"
            );
        }

        return attendanceDAO.getAttendanceById(attendanceId);
    }

    // --------------------------getAttendanceByEmployee---------------------------------------------------

    @Override
    public List<Attendance> getAttendanceByEmployeeId(
            int employeeId,
            LocalDate startDate,
            LocalDate endDate) {

        if (employeeId <= 0) {
            throw new IllegalArgumentException(
                    "Employee ID must be greater than 0"
            );
        }

        if (startDate == null) {
            throw new IllegalArgumentException(
                    "Start date cannot be null"
            );
        }

        if (endDate == null) {
            throw new IllegalArgumentException(
                    "End date cannot be null"
            );
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    "Start date cannot be after end date"
            );
        }

        return attendanceDAO.getAttendanceByEmployeeId(employeeId);
    }

    // --------------------------getAttendanceByDate---------------------------------------------------

    @Override
    public List<Attendance> getAttendanceByDate(
            LocalDate attendanceDate) {

        if (attendanceDate == null) {
            throw new IllegalArgumentException(
                    "Attendance date cannot be null"
            );
        }

        return attendanceDAO.getAttendanceByDate(
                attendanceDate
        );
    }

    // --------------------------updateAttendance---------------------------------------------------

    @Override
    public void updateAttendance(Attendance attendance) {

        if (attendance == null) {
            throw new IllegalArgumentException(
                    "Attendance cannot be null"
            );
        }

        if (attendance.getAttendanceId() <= 0) {
            throw new IllegalArgumentException(
                    "Attendance ID must be greater than 0"
            );
        }

        validateAttendance(attendance);

        attendanceDAO.updateAttendance(attendance);
    }

    // --------------------------deleteAttendance---------------------------------------------------

    @Override
    public void deleteAttendance(int attendanceId) {

        if (attendanceId <= 0) {
            throw new IllegalArgumentException(
                    "Attendance ID must be greater than 0"
            );
        }

        attendanceDAO.deleteAttendance(attendanceId);
    }

    // --------------------------validateAttendance---------------------------------------------------

    private void validateAttendance(Attendance attendance) {

        if (attendance == null) {
            throw new IllegalArgumentException(
                    "Attendance cannot be null"
            );
        }

        if (attendance.getEmployeeId() <= 0) {
            throw new IllegalArgumentException(
                    "Employee ID must be greater than 0"
            );
        }

        if (attendance.getAttendanceDate() == null) {
            throw new IllegalArgumentException(
                    "Attendance date cannot be null"
            );
        }

        if (attendance.getAttendanceDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Attendance date cannot be in the future"
            );
        }

        String status = attendance.getStatus();

        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException(
                    "Attendance status cannot be blank"
            );
        }

        if (!status.equals("PRESENT")
                && !status.equals("ABSENT")
                && !status.equals("LEAVE")) {

            throw new IllegalArgumentException(
                    "Invalid attendance status"
            );
        }

        LocalTime checkIn = attendance.getCheckIn();
        LocalTime checkOut = attendance.getCheckOut();

        if (status.equals("PRESENT")) {

            if (checkIn == null) {
                throw new IllegalArgumentException(
                        "Check-in time is required for PRESENT status"
                );
            }

            if (checkOut != null && checkOut.isBefore(checkIn)) {
                throw new IllegalArgumentException(
                        "Check-out time cannot be before check-in time"
                );
            }
        }

        if (status.equals("ABSENT")
                || status.equals("LEAVE")) {

            if (checkIn != null || checkOut != null) {
                throw new IllegalArgumentException(
                        "Check-in and check-out must be null for "
                        + status
                        + " status"
                );
            }
        }
    }
}