package com.project.sportsManagement.repo;

import com.project.sportsManagement.entity.Location;
import com.project.sportsManagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByEmail(String email);

    Student findByStudentId(int StudentId);
}
