package com.test_generator.questions.difficulty.application;

import com.test_generator.questions.difficulty.domain.Difficulty;
import com.test_generator.questions.difficulty.infraestructure.dtos.input.DifficultyInputDto;
import com.test_generator.shared.exceptions.NotFoundException;

public interface PutDifficultyService {

    Difficulty updateById(Integer id, DifficultyInputDto difficultyInputDto) throws NotFoundException;
}
