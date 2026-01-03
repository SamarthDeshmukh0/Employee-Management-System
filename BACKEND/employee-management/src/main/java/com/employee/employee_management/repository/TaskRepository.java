package com.employee.employee_management.repository;

import com.employee.employee_management.model.Task;
import com.employee.employee_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedToOrderByCreatedAtDesc(User assignedTo);
    List<Task> findByAssignedByOrderByCreatedAtDesc(User assignedBy);
    List<Task> findByStatusOrderByCreatedAtDesc(String status);
    List<Task> findByPriorityOrderByCreatedAtDesc(String priority);
}