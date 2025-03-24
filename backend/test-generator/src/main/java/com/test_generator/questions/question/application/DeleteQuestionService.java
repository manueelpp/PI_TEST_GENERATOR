package com.test_generator.questions.question.application;

import com.test_generator.shared.exceptions.NotFoundException;

public interface DeleteQuestionService {

    void deleteById(Integer id) throws NotFoundException;
}
