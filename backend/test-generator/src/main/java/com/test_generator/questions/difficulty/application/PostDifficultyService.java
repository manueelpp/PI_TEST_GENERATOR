package com.test_generator.questions.difficulty.application;

import com.test_generator.questions.difficulty.domain.Difficulty;
import com.test_generator.questions.difficulty.infraestructure.dtos.input.DifficultyInputDto;

public interface PostDifficultyService {

    Difficulty createDifficulty(DifficultyInputDto difficultyInputDto);
}
