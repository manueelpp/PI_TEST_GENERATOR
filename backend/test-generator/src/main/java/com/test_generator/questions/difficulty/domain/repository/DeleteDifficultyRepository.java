package com.test_generator.questions.difficulty.domain.repository;

import com.test_generator.shared.exceptions.NotFoundException;

public interface DeleteDifficultyRepository {

    void deleteById(Integer id) throws NotFoundException;
}
