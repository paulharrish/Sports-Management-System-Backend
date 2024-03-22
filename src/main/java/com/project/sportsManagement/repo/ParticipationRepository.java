package com.project.sportsManagement.repo;

import com.project.sportsManagement.entity.Participation;
import com.project.sportsManagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation,Integer> {
    List<Participation> findAllByStudent(Student student);
//    @Query(value = "value = INSERT INTO participation (student_id, game_code) VALUES (:studentId, :gameCode) " , nativeQuery = true)
//    void participateInAEvent(@Param("studentId") int studentId, @Param("gameCode") int gameCode);
}
