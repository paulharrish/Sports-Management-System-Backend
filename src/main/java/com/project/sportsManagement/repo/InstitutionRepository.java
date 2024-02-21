package com.project.sportsManagement.repo;

import com.project.sportsManagement.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstitutionRepository extends JpaRepository<Institution,Integer> {

    Optional<Institution> findByEmail(String email);
}
