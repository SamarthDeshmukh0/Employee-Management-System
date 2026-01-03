package com.employee.employee_management.dto;

import com.employee.employee_management.model.Role;

public class LoginResponse {
    
    private String token;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    
    // Constructors
    public LoginResponse() {}
    
    public LoginResponse(String token, Long userId, String firstName, String lastName, String email, Role role) {
        this.token = token;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role.name();
    }
    
    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role.name();
    }
}