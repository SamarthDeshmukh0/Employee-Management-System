package com.employee.employee_management.controller;

import com.employee.employee_management.model.Task;
import com.employee.employee_management.model.TaskComment;
import com.employee.employee_management.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:3000")
public class TaskController {
    
    private final TaskService taskService;
    
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    // Create task
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            System.out.println("TaskController: Received task creation request");
            System.out.println("Task title: " + task.getTitle());
            System.out.println("Assigned to ID: " + (task.getAssignedTo() != null ? task.getAssignedTo().getId() : "null"));
            System.out.println("Assigned by ID: " + (task.getAssignedBy() != null ? task.getAssignedBy().getId() : "null"));
            
            Task created = taskService.createTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            System.err.println("TaskController: Error - " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            System.err.println("TaskController: Unexpected error - " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "Database error: User ID not found or invalid. Please check that both assigned_to and assigned_by users exist.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    // Get all tasks
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
    
    // Get tasks assigned to user
    @GetMapping("/assigned-to/{userId}")
    public ResponseEntity<?> getTasksAssignedToUser(@PathVariable Long userId) {
        try {
            List<Task> tasks = taskService.getTasksAssignedToUser(userId);
            return ResponseEntity.ok(tasks);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    // Get tasks assigned by user (Manager)
    @GetMapping("/assigned-by/{userId}")
    public ResponseEntity<?> getTasksAssignedByUser(@PathVariable Long userId) {
        try {
            List<Task> tasks = taskService.getTasksAssignedByUser(userId);
            return ResponseEntity.ok(tasks);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    // Update task
    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId, @RequestBody Task task) {
        try {
            Task updated = taskService.updateTask(taskId, task);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    // Delete task
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        try {
            taskService.deleteTask(taskId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Task deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    // Add comment to task
    @PostMapping("/{taskId}/comments")
    public ResponseEntity<?> addComment(@PathVariable Long taskId, 
                                       @RequestParam Long userId,
                                       @RequestParam String comment) {
        try {
            TaskComment created = taskService.addComment(taskId, userId, comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    // Get task comments
    @GetMapping("/{taskId}/comments")
    public ResponseEntity<?> getTaskComments(@PathVariable Long taskId) {
        try {
            List<TaskComment> comments = taskService.getTaskComments(taskId);
            return ResponseEntity.ok(comments);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}