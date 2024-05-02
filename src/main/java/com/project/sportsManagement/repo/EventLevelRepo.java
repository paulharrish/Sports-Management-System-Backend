package com.project.sportsManagement.repo;

import com.project.sportsManagement.entity.EventLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventLevelRepo extends JpaRepository<EventLevel,Integer> {
}
