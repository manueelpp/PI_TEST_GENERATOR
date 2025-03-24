package com.test_generator.questions.subjects.topic.infraestructure.repository;

import com.test_generator.questions.subjects.topic.infraestructure.repository.entity.TopicJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicJpaRepository extends JpaRepository<TopicJpa, Integer> {

    Optional<TopicJpa> findByNameIgnoreCase(String name);

    Optional<TopicJpa> findByNameIgnoreCaseAndSubject_NameIgnoreCase(String name, String subjectName);

    Page<TopicJpa> findAllBySubject_Id(Pageable pageable, Integer id);
}
