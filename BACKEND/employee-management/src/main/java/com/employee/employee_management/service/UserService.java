package com.employee.employee_management.service;

import com.employee.employee_management.dto.LoginRequest;
import com.employee.employee_management.dto.LoginResponse;
import com.employee.employee_management.dto.SignupRequest;
import com.employee.employee_management.model.User;
import com.employee.employee_management.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final JwtService jwtService;
    
    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }
    
    public User signup(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User();
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword()); // Store password as is (no encryption)
        user.setRole(signupRequest.getRole()); // Use the role from signup request
        
        return userRepository.save(user);
    }
    
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        
        // Direct password comparison (no encryption)
        if (!loginRequest.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        
        String token = jwtService.generateToken(user.getEmail());
        
        return new LoginResponse(
                token,
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}