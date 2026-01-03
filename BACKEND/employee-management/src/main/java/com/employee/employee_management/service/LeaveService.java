package com.employee.employee_management.service;

import com.employee.employee_management.model.LeaveApplication;
import com.employee.employee_management.model.User;
import com.employee.employee_management.repository.LeaveApplicationRepository;
import com.employee.employee_management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaveService {
    
    private final LeaveApplicationRepository leaveRepository;
    private final UserRepository userRepository;
    
    public LeaveService(LeaveApplicationRepository leaveRepository, UserRepository userRepository) {
        this.leaveRepository = leaveRepository;
        this.userRepository = userRepository;
    }
    
    // Apply for leave
    public LeaveApplication applyLeave(LeaveApplication leaveApplication) {
        leaveApplication.setStatus("PENDING");
        return leaveRepository.save(leaveApplication);
    }
    
    // Get user's leaves
    public List<LeaveApplication> getUserLeaves(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return leaveRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    // Get all leaves (Admin/Manager)
    public List<LeaveApplication> getAllLeaves() {
        return leaveRepository.findAllByOrderByCreatedAtDesc();
    }
    
    // Get pending leaves
    public List<LeaveApplication> getPendingLeaves() {
        return leaveRepository.findByStatusOrderByCreatedAtDesc("PENDING");
    }
    
    // Approve leave
    public LeaveApplication approveLeave(Long leaveId, Long approverId) {
        LeaveApplication leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave application not found"));
        
        User approver = userRepository.findById(approverId)
                .orElseThrow(() -> new RuntimeException("Approver not found"));
        
        leave.setStatus("APPROVED");
        leave.setApprovedBy(approver);
        leave.setApprovedAt(LocalDateTime.now());
        
        return leaveRepository.save(leave);
    }
    
    // Reject leave
    public LeaveApplication rejectLeave(Long leaveId, Long approverId, String reason) {
        LeaveApplication leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave application not found"));
        
        User approver = userRepository.findById(approverId)
                .orElseThrow(() -> new RuntimeException("Approver not found"));
        
        leave.setStatus("REJECTED");
        leave.setApprovedBy(approver);
        leave.setApprovedAt(LocalDateTime.now());
        leave.setRejectionReason(reason);
        
        return leaveRepository.save(leave);
    }
}