package com.project.sportsManagement.repo;

import com.project.sportsManagement.entity.EventGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventGameRepository extends JpaRepository<EventGame,Integer> {
}
