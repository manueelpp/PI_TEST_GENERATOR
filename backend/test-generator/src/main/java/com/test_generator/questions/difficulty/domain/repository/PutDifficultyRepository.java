package com.test_generator.questions.difficulty.domain.repository;

import com.test_generator.questions.difficulty.domain.Difficulty;

public interface PutDifficultyRepository {

    Difficulty updateDifficulty(Difficulty difficulty);
}
