package com.project.sportsManagement.repo;

import com.project.sportsManagement.entity.Participation;
import com.project.sportsManagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation,Integer> {
    List<Participation> findAllByStudent(Student student);
}
