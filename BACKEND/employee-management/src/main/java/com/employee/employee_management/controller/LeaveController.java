package com.employee.employee_management.controller;

import com.employee.employee_management.model.LeaveApplication;
import com.employee.employee_management.service.LeaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "http://localhost:3000")
public class LeaveController {
    
    private final LeaveService leaveService;
    
    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }
    
    // Apply for leave
    @PostMapping
    public ResponseEntity<?> applyLeave(@RequestBody LeaveApplication leaveApplication) {
        try {
            LeaveApplication created = leaveService.applyLeave(leaveApplication);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    // Get user's leaves
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserLeaves(@PathVariable Long userId) {
        try {
            List<LeaveApplication> leaves = leaveService.getUserLeaves(userId);
            return ResponseEntity.ok(leaves);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    // Get all leaves (Admin/Manager)
    @GetMapping("/all")
    public ResponseEntity<List<LeaveApplication>> getAllLeaves() {
        List<LeaveApplication> leaves = leaveService.getAllLeaves();
        return ResponseEntity.ok(leaves);
    }
    
    // Get pending leaves
    @GetMapping("/pending")
    public ResponseEntity<List<LeaveApplication>> getPendingLeaves() {
        List<LeaveApplication> leaves = leaveService.getPendingLeaves();
        return ResponseEntity.ok(leaves);
    }
    
    // Approve leave
    @PutMapping("/{leaveId}/approve")
    public ResponseEntity<?> approveLeave(@PathVariable Long leaveId, @RequestParam Long approverId) {
        try {
            LeaveApplication leave = leaveService.approveLeave(leaveId, approverId);
            return ResponseEntity.ok(leave);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    // Reject leave
    @PutMapping("/{leaveId}/reject")
    public ResponseEntity<?> rejectLeave(@PathVariable Long leaveId, 
                                        @RequestParam Long approverId,
                                        @RequestParam String reason) {
        try {
            LeaveApplication leave = leaveService.rejectLeave(leaveId, approverId, reason);
            return ResponseEntity.ok(leave);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}