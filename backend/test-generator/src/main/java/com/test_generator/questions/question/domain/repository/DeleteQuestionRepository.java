package com.test_generator.questions.question.domain.repository;

import com.test_generator.shared.exceptions.NotFoundException;

public interface DeleteQuestionRepository {

    void deleteById(Integer id) throws NotFoundException;
}
