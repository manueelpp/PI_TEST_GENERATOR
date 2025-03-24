package com.test_generator.questions.subjects.subject.domain.repository;

import com.test_generator.shared.exceptions.NotFoundException;

public interface DeleteSubjectRepository {

    void deleteById(Integer id) throws NotFoundException;
}
