package com.test_generator.questions.difficulty.application.impl;

import com.test_generator.questions.difficulty.application.GetDifficultyService;
import com.test_generator.questions.difficulty.application.PutDifficultyService;
import com.test_generator.questions.difficulty.application.mapper.DifficultyMapper;
import com.test_generator.questions.difficulty.domain.Difficulty;
import com.test_generator.questions.difficulty.domain.repository.PutDifficultyRepository;
import com.test_generator.questions.difficulty.infraestructure.dtos.input.DifficultyInputDto;
import com.test_generator.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PutDifficultyServiceImpl implements PutDifficultyService {

    private final DifficultyMapper difficultyMapper;
    private final PutDifficultyRepository putDifficultyRepository;
    private final GetDifficultyService getDifficultyService;

    @Override
    public Difficulty updateById(Integer id, DifficultyInputDto difficultyInputDto) throws NotFoundException {

        Difficulty difficulty = getDifficultyService.findById(id);

        Difficulty updatedDifficulty = difficultyMapper.update(difficulty, difficultyInputDto);

        return putDifficultyRepository.updateDifficulty(updatedDifficulty);
    }
}
