package com.test_generator.questions.difficulty.infraestructure.repository.impl;

import com.test_generator.questions.difficulty.application.mapper.DifficultyMapper;
import com.test_generator.questions.difficulty.domain.Difficulty;
import com.test_generator.questions.difficulty.domain.repository.PutDifficultyRepository;
import com.test_generator.questions.difficulty.infraestructure.repository.DifficultyJpaRepository;
import com.test_generator.questions.difficulty.infraestructure.repository.entity.DifficultyJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PutDifficultyRepositoryImpl implements PutDifficultyRepository {

    private final DifficultyMapper difficultyMapper;
    private final DifficultyJpaRepository difficultyJpaRepository;
    @Override
    public Difficulty updateDifficulty(Difficulty difficulty) {

        DifficultyJpa difficultyJpa= difficultyJpaRepository.save(
                difficultyMapper.domainToJpa(difficulty)
        );

        return difficultyMapper.jpaToDomain(difficultyJpa);
    }
}
