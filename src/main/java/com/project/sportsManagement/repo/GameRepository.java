package com.project.sportsManagement.repo;

import com.project.sportsManagement.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game,Integer> {
}
