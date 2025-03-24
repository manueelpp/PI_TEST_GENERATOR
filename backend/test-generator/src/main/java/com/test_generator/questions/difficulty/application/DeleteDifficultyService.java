package com.test_generator.questions.difficulty.application;

import com.test_generator.shared.exceptions.NotFoundException;

public interface DeleteDifficultyService {

    void deleteById(Integer id) throws NotFoundException;
}
