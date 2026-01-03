package com.employee.employee_management.repository;

import com.employee.employee_management.model.Task;
import com.employee.employee_management.model.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
    List<TaskComment> findByTaskOrderByCreatedAtAsc(Task task);
}