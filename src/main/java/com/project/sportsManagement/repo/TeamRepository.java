package com.project.sportsManagement.repo;

import com.project.sportsManagement.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,Integer> {
}
