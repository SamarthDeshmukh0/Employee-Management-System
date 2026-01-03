package com.employee.employee_management.repository;

import com.employee.employee_management.model.LeaveApplication;
import com.employee.employee_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {
    List<LeaveApplication> findByUserOrderByCreatedAtDesc(User user);
    List<LeaveApplication> findByStatusOrderByCreatedAtDesc(String status);
    List<LeaveApplication> findAllByOrderByCreatedAtDesc();
}