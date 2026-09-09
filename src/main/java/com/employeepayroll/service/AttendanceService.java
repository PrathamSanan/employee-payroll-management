package com.employeepayroll.service;

import java.time.LocalDate;
import java.util.List;

import com.employeepayroll.model.Attendance;

public interface AttendanceService {

    void addAttendance(Attendance attendance);

    Attendance getAttendanceById(int attendanceId);

    List<Attendance> getAttendanceByEmployeeId(
            int employeeId,
            LocalDate startDate,
            LocalDate endDate
    );

    List<Attendance> getAttendanceByDate(LocalDate attendanceDate);

    void updateAttendance(Attendance attendance);

    void deleteAttendance(int attendanceId);
}