package com.employee.employee_management.repository;

import com.employee.employee_management.model.Attendance;
import com.employee.employee_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByUserAndDate(User user, LocalDate date);
    List<Attendance> findByUserOrderByDateDesc(User user);
    List<Attendance> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
    boolean existsByUserAndDate(User user, LocalDate date);
}