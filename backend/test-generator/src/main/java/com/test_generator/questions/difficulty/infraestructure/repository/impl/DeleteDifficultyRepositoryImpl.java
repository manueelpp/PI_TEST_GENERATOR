package com.test_generator.questions.difficulty.infraestructure.repository.impl;

import com.test_generator.questions.difficulty.domain.repository.DeleteDifficultyRepository;
import com.test_generator.questions.difficulty.infraestructure.repository.DifficultyJpaRepository;
import com.test_generator.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.test_generator.shared.constants.Constants.DIFFICULTY_ENTITY;

@Repository
@RequiredArgsConstructor
public class DeleteDifficultyRepositoryImpl implements DeleteDifficultyRepository {

    private final DifficultyJpaRepository difficultyJpaRepository;

    @Override
    public void deleteById(Integer id) throws NotFoundException {

        difficultyJpaRepository.deleteById(difficultyJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(DIFFICULTY_ENTITY, id))
                .getId()
        );
    }
}
