package com.employee.employee_management.controller;

import com.employee.employee_management.model.Attendance;
import com.employee.employee_management.service.AttendanceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:3000")
public class AttendanceController {
    
    private final AttendanceService attendanceService;
    
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }
    
    // Clock In
    @PostMapping("/clock-in/{userId}")
    public ResponseEntity<?> clockIn(@PathVariable Long userId) {
        try {
            Attendance attendance = attendanceService.clockIn(userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(attendance);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    // Clock Out
    @PutMapping("/clock-out/{userId}")
    public ResponseEntity<?> clockOut(@PathVariable Long userId) {
        try {
            Attendance attendance = attendanceService.clockOut(userId);
            return ResponseEntity.ok(attendance);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    // Get user attendance
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserAttendance(@PathVariable Long userId) {
        try {
            List<Attendance> attendance = attendanceService.getUserAttendance(userId);
            return ResponseEntity.ok(attendance);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    // Get attendance by date range
    @GetMapping("/user/{userId}/range")
    public ResponseEntity<?> getAttendanceByDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<Attendance> attendance = attendanceService.getAttendanceByDateRange(userId, startDate, endDate);
            return ResponseEntity.ok(attendance);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    // Get all attendance (Admin)
    @GetMapping("/all")
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        List<Attendance> attendance = attendanceService.getAllAttendance();
        return ResponseEntity.ok(attendance);
    }
}