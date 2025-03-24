package com.test_generator.questions.difficulty.infraestructure.repository.impl;


import com.test_generator.questions.difficulty.application.mapper.DifficultyMapper;
import com.test_generator.questions.difficulty.domain.Difficulty;
import com.test_generator.questions.difficulty.domain.repository.PostDifficultyRepository;
import com.test_generator.questions.difficulty.infraestructure.repository.DifficultyJpaRepository;
import com.test_generator.questions.difficulty.infraestructure.repository.entity.DifficultyJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostDifficultyRepositoryImpl implements PostDifficultyRepository {

    private final DifficultyJpaRepository difficultyJpaRepository;
    private final DifficultyMapper difficultyMapper;

    @Override
    public Difficulty createDifficulty(Difficulty difficulty) {

        DifficultyJpa difficultyJpa = difficultyJpaRepository.save(
                difficultyMapper.domainToJpa(difficulty)
        );

        return difficultyMapper.jpaToDomain(difficultyJpa);
    }
}
