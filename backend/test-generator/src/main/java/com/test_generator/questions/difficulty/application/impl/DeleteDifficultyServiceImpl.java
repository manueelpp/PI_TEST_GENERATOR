package com.test_generator.questions.difficulty.application.impl;

import com.test_generator.questions.difficulty.application.DeleteDifficultyService;
import com.test_generator.questions.difficulty.domain.repository.DeleteDifficultyRepository;
import com.test_generator.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteDifficultyServiceImpl implements DeleteDifficultyService {

    private final DeleteDifficultyRepository deleteDifficultyRepository;

    @Override
    public void deleteById(Integer id) throws NotFoundException {

        deleteDifficultyRepository.deleteById(id);
    }
}
