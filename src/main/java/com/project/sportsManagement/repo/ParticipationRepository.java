package com.project.sportsManagement.repo;

import com.project.sportsManagement.entity.Participation;
import com.project.sportsManagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation,Integer> {
    List<Participation> findAllByStudent(Student student);

    Optional<Participation> findByStudentStudentIdAndGameCodeGameCode(int studentId, int gameCode);

    Optional<Participation> findByTeamTeamIdAndGameCodeGameCode(int teamId, int gameCode);
}
