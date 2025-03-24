package com.test_generator.questions.subjects.subject.application;

import com.test_generator.shared.configuration.CustomPage;
import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.subject.domain.Subject;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface GetSubjectService {

    Subject findById(Integer id) throws NotFoundException;

    Optional<Subject> findByName(String name) throws NotFoundException;

    CustomPage<Subject> findAll(PageRequest pageRequest);
}
