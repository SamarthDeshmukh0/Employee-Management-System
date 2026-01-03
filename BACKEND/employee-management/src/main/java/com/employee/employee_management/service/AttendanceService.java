package com.employee.employee_management.service;

import com.employee.employee_management.model.Attendance;
import com.employee.employee_management.model.User;
import com.employee.employee_management.repository.AttendanceRepository;
import com.employee.employee_management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {
    
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    
    public AttendanceService(AttendanceRepository attendanceRepository, UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
    }
    
    // Clock In
    public Attendance clockIn(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        LocalDate today = LocalDate.now();
        
        // Check if already clocked in today
        if (attendanceRepository.existsByUserAndDate(user, today)) {
            throw new RuntimeException("Already clocked in today");
        }
        
        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setClockIn(LocalDateTime.now());
        attendance.setDate(today);
        attendance.setStatus("PRESENT");
        
        return attendanceRepository.save(attendance);
    }
    
    // Clock Out
    public Attendance clockOut(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findByUserAndDate(user, today)
                .orElseThrow(() -> new RuntimeException("Not clocked in today"));
        
        if (attendance.getClockOut() != null) {
            throw new RuntimeException("Already clocked out");
        }
        
        attendance.setClockOut(LocalDateTime.now());
        return attendanceRepository.save(attendance);
    }
    
    // Get user attendance
    public List<Attendance> getUserAttendance(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return attendanceRepository.findByUserOrderByDateDesc(user);
    }
    
    // Get attendance for date range
    public List<Attendance> getAttendanceByDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return attendanceRepository.findByUserAndDateBetween(user, startDate, endDate);
    }
    
    // Get all attendance (Admin)
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }
}