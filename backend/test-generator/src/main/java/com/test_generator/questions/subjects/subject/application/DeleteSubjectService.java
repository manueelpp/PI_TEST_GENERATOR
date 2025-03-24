package com.test_generator.questions.subjects.subject.application;

import com.test_generator.shared.exceptions.NotFoundException;

public interface DeleteSubjectService {

    void deleteById(Integer id) throws NotFoundException;
}
