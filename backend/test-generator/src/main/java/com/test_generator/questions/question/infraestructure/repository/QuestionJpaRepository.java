package com.test_generator.questions.question.infraestructure.repository;

import com.test_generator.questions.question.infraestructure.repository.entity.QuestionJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface QuestionJpaRepository extends JpaRepository<QuestionJpa, Integer> {

    Page<QuestionJpa> findByGeneratedAtAfter(Pageable pageable, LocalDateTime localDateTime);
}
