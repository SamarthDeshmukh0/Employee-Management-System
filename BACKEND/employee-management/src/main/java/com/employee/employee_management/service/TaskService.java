package com.employee.employee_management.service;

import com.employee.employee_management.model.Task;
import com.employee.employee_management.model.TaskComment;
import com.employee.employee_management.model.User;
import com.employee.employee_management.repository.TaskCommentRepository;
import com.employee.employee_management.repository.TaskRepository;
import com.employee.employee_management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final TaskCommentRepository taskCommentRepository;
    private final UserRepository userRepository;
    
    public TaskService(TaskRepository taskRepository, TaskCommentRepository taskCommentRepository, 
                      UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskCommentRepository = taskCommentRepository;
        this.userRepository = userRepository;
    }
    
    // Create task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
    
    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    // Get tasks assigned to user
    public List<Task> getTasksAssignedToUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return taskRepository.findByAssignedToOrderByCreatedAtDesc(user);
    }
    
    // Get tasks assigned by user (Manager)
    public List<Task> getTasksAssignedByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return taskRepository.findByAssignedByOrderByCreatedAtDesc(user);
    }
    
    // Update task
    public Task updateTask(Long taskId, Task taskDetails) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        task.setPriority(taskDetails.getPriority());
        task.setDeadline(taskDetails.getDeadline());
        
        return taskRepository.save(task);
    }
    
    // Delete task
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(task);
    }
    
    // Add comment to task
    public TaskComment addComment(Long taskId, Long userId, String commentText) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        TaskComment comment = new TaskComment(task, user, commentText);
        return taskCommentRepository.save(comment);
    }
    
    // Get task comments
    public List<TaskComment> getTaskComments(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return taskCommentRepository.findByTaskOrderByCreatedAtAsc(task);
    }
}