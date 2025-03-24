package com.test_generator.questions.difficulty.application.impl;

import com.test_generator.questions.difficulty.application.PostDifficultyService;
import com.test_generator.questions.difficulty.application.mapper.DifficultyMapper;
import com.test_generator.questions.difficulty.domain.Difficulty;
import com.test_generator.questions.difficulty.domain.repository.PostDifficultyRepository;
import com.test_generator.questions.difficulty.infraestructure.dtos.input.DifficultyInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostDifficultyServiceImpl implements PostDifficultyService {

    private final PostDifficultyRepository postDifficultyRepository;
    private final DifficultyMapper difficultyMapper;

    @Override
    public Difficulty createDifficulty(DifficultyInputDto difficultyInputDto) {

        return postDifficultyRepository.createDifficulty(
                difficultyMapper.inputToDomain(difficultyInputDto)
        );
    }
}
