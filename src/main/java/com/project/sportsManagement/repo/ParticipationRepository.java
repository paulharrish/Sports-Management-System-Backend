package com.project.sportsManagement.repo;

import com.project.sportsManagement.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation,Integer> {


    @Query(value = "SELECT p.*,st.student_id AS team_student_id from participation p "+
    "LEFT JOIN student_team st on p.team_id = st.team_id "+
            "where p.student_id = :studentId OR st.student_id = :studentId",nativeQuery = true)
    List<Participation> findAllByStudent(@Param("studentId") int studentId);

    Optional<Participation> findByStudentStudentIdAndGameCodeGameCode(int studentId, int gameCode);

    Optional<Participation> findByTeamTeamIdAndGameCodeGameCode(int teamId, int gameCode);




}
