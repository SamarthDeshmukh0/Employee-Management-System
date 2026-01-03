package com.employee.employee_management.controller;

import com.employee.employee_management.model.User;
import com.employee.employee_management.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
    
    private final AdminService adminService;
    
    public EmployeeController(AdminService adminService) {
        this.adminService = adminService;
    }
    
    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        try {
            System.out.println("EmployeeController: Received request to get all employees");
            List<User> employees = adminService.getAllEmployees();
            System.out.println("EmployeeController: Returning " + employees.size() + " employees");
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            System.err.println("EmployeeController: Error getting employees - " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error fetching employees: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @GetMapping("/count")
    public ResponseEntity<?> getTotalEmployeeCount() {
        try {
            System.out.println("EmployeeController: Received request to get employee count");
            long count = adminService.getTotalEmployeeCount();
            Map<String, Long> response = new HashMap<>();
            response.put("count", count);
            System.out.println("EmployeeController: Returning count: " + count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("EmployeeController: Error getting count - " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error fetching count: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        try {
            System.out.println("EmployeeController: Getting employee with id: " + id);
            User employee = adminService.getEmployeeById(id);
            return ResponseEntity.ok(employee);
        } catch (RuntimeException e) {
            System.err.println("EmployeeController: Employee not found - " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            System.err.println("EmployeeController: Error - " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody User employee) {
        try {
            System.out.println("EmployeeController: Received request to create employee");
            System.out.println("Employee data: " + employee.getFirstName() + " " + employee.getLastName() + " - " + employee.getEmail());
            User createdEmployee = adminService.createEmployee(employee);
            System.out.println("EmployeeController: Employee created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (RuntimeException e) {
            System.err.println("EmployeeController: Error creating employee - " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            System.err.println("EmployeeController: Unexpected error - " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error creating employee: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody User employee) {
        try {
            System.out.println("EmployeeController: Updating employee with id: " + id);
            System.out.println("EmployeeController: Received data - Name: " + employee.getFirstName() + " " + employee.getLastName());
            System.out.println("EmployeeController: Received role: " + employee.getRole());
            
            User updatedEmployee = adminService.updateEmployee(id, employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException e) {
            System.err.println("EmployeeController: Error updating employee - " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            System.err.println("EmployeeController: Unexpected error - " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error updating employee: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            System.out.println("EmployeeController: Deleting employee with id: " + id);
            adminService.deleteEmployee(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Employee deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.err.println("EmployeeController: Error deleting employee - " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            System.err.println("EmployeeController: Unexpected error - " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error deleting employee: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}