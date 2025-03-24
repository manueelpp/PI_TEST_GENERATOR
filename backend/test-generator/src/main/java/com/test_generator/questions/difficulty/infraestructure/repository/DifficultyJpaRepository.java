package com.test_generator.questions.difficulty.infraestructure.repository;

import com.test_generator.questions.difficulty.infraestructure.repository.entity.DifficultyJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DifficultyJpaRepository extends JpaRepository<DifficultyJpa, Integer> {

    Optional<DifficultyJpa> findByNameIgnoreCase(String name);
}
