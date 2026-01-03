package com.employee.employee_management.controller;

import com.employee.employee_management.model.Department;
import com.employee.employee_management.repository.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "http://localhost:3000")
public class DepartmentController {
    
    private final DepartmentRepository departmentRepository;
    
    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    
    // Get all departments
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return ResponseEntity.ok(departments);
    }
    
    // Get department by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Long id) {
        return departmentRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    Map<String, String> error = new HashMap<>();
                    error.put("message", "Department not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                });
    }
    
    // Create department
    @PostMapping
    public ResponseEntity<?> createDepartment(@RequestBody Department department) {
        try {
            if (departmentRepository.existsByName(department.getName())) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Department with this name already exists");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            Department created = departmentRepository.save(department);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error creating department: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // Update department
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody Department departmentDetails) {
        return departmentRepository.findById(id)
                .<ResponseEntity<?>>map(department -> {
                    department.setName(departmentDetails.getName());
                    department.setDescription(departmentDetails.getDescription());
                    Department updated = departmentRepository.save(department);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> {
                    Map<String, String> error = new HashMap<>();
                    error.put("message", "Department not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                });
    }
    
    // Delete department
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        return departmentRepository.findById(id)
                .<ResponseEntity<?>>map(department -> {
                    departmentRepository.delete(department);
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Department deleted successfully");
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    Map<String, String> error = new HashMap<>();
                    error.put("message", "Department not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                });
    }
}