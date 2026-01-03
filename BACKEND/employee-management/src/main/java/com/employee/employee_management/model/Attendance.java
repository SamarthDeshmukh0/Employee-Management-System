package com.employee.employee_management.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "date"})
})
public class Attendance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "clock_in", nullable = false)
    private LocalDateTime clockIn;
    
    @Column(name = "clock_out")
    private LocalDateTime clockOut;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(length = 20)
    private String status = "PRESENT"; // PRESENT, ABSENT, HALF_DAY, LEAVE
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (date == null) {
            date = LocalDate.now();
        }
    }
    
    // Constructors
    public Attendance() {}
    
    public Attendance(User user, LocalDateTime clockIn) {
        this.user = user;
        this.clockIn = clockIn;
        this.date = clockIn.toLocalDate();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public LocalDateTime getClockIn() {
        return clockIn;
    }
    
    public void setClockIn(LocalDateTime clockIn) {
        this.clockIn = clockIn;
    }
    
    public LocalDateTime getClockOut() {
        return clockOut;
    }
    
    public void setClockOut(LocalDateTime clockOut) {
        this.clockOut = clockOut;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}