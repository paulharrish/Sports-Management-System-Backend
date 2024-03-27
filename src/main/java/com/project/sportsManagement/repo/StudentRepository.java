package com.project.sportsManagement.repo;

import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByEmail(String email);

    Student findByStudentId(int StudentId);

    Optional<Student> findByRollNo(String rollNo);

    @Query("Select t from Team t join t.teamMembers m where m =:student")
    List<Team> getAllTeams(@Param("student") Student student);
}
