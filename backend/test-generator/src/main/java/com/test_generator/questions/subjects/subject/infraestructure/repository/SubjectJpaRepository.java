package com.test_generator.questions.subjects.subject.infraestructure.repository;

import com.test_generator.questions.subjects.subject.infraestructure.repository.entity.SubjectJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectJpaRepository extends JpaRepository<SubjectJpa, Integer> {

    Optional<SubjectJpa> findByNameIgnoreCase(String name);
}
