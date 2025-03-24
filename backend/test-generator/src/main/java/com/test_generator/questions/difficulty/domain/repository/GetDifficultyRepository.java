package com.test_generator.questions.difficulty.domain.repository;

import com.test_generator.questions.difficulty.domain.Difficulty;
import com.test_generator.shared.configuration.CustomPage;
import com.test_generator.shared.exceptions.NotFoundException;
import org.springframework.data.domain.PageRequest;

public interface GetDifficultyRepository {

    Difficulty findById(Integer id) throws NotFoundException;

    Difficulty findByName(String name) throws NotFoundException;

    CustomPage<Difficulty> findAll(PageRequest pageRequest);
}
