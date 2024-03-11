package com.project.sportsManagement.repo;

import com.project.sportsManagement.entity.Event;
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
}
