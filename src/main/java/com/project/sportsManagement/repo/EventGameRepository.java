package com.project.sportsManagement.repo;

import com.project.sportsManagement.entity.EventGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface EventGameRepository extends JpaRepository<EventGame,Integer> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM event_game WHERE event_id = :eventId", nativeQuery = true)
    void deleteByEventId(@Param("eventId") int eventId);
}
