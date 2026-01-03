package com.employee.employee_management.service;

import com.employee.employee_management.model.User;
import com.employee.employee_management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    
    private final UserRepository userRepository;
    
    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public List<User> getAllEmployees() {
        System.out.println("AdminService: Getting all employees...");
        List<User> users = userRepository.findAll();
        System.out.println("AdminService: Found " + users.size() + " users");
        return users;
    }
    
    public User getEmployeeById(Long id) {
        System.out.println("AdminService: Getting employee with id: " + id);
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }
    
    public User createEmployee(User user) {
        System.out.println("AdminService: Creating employee with email: " + user.getEmail());
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Employee with this email already exists");
        }
        // Keep the role as provided (EMPLOYEE or ADMIN)
        User savedUser = userRepository.save(user);
        System.out.println("AdminService: Employee created successfully with id: " + savedUser.getId());
        return savedUser;
    }
    
    public User updateEmployee(Long id, User employeeDetails) {
        System.out.println("AdminService: Updating employee with id: " + id);
        System.out.println("AdminService: New role: " + employeeDetails.getRole());
        
        User user = getEmployeeById(id);
        
        user.setFirstName(employeeDetails.getFirstName());
        user.setLastName(employeeDetails.getLastName());
        user.setEmail(employeeDetails.getEmail());
        user.setPassword(employeeDetails.getPassword());
        
        // Handle role update - if null, keep existing role
        if (employeeDetails.getRole() != null) {
            user.setRole(employeeDetails.getRole());
        }
        
        User updatedUser = userRepository.save(user);
        System.out.println("AdminService: Employee updated successfully");
        return updatedUser;
    }
    
    public void deleteEmployee(Long id) {
        System.out.println("AdminService: Deleting employee with id: " + id);
        User user = getEmployeeById(id);
        userRepository.delete(user);
        System.out.println("AdminService: Employee deleted successfully");
    }
    
    public long getTotalEmployeeCount() {
        long count = userRepository.count();
        System.out.println("AdminService: Total employee count: " + count);
        return count;
    }
}