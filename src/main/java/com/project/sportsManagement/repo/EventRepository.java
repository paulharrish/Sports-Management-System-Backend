package com.project.sportsManagement.repo;

import com.project.sportsManagement.entity.Event;
import com.project.sportsManagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EventRepository extends JpaRepository<Event,Integer> {

    @Query("select e from Event e " +
            "where lower(e.eventName) like lower(concat('%',:searchTerm,'%'))")
    List<Event> search(@Param("searchTerm") String filterText);


 @Query("select e from Event e "+
         "join Institution i on e.host = i "+
 "where lower(i.institutionName) like lower(concat('%',:searchTerm,'%'))")
    List<Event> searchByCollege( @Param("searchTerm") String filterText);

 @Query("select e from Event e " +
 "join EventGame eg on eg.eventId = e " +
 "join Participation p on p.gameCode = eg " +
 "where p.student = :student")
    List<Event> getParticipatedEvents(@Param("student") Student Student);


    @Query("select s from Student s "+
            "join Participation p on p.student = s "+
            "join EventGame eg on p.gameCode = eg "+
            "join Event e on eg.eventId = e "+
            "where e.eventId = :eventId")
    List<Student> getSoloParticipantsInAEvent(int eventId);



}
