package com.test_generator.questions.difficulty.application.impl;

import com.test_generator.questions.difficulty.application.GetDifficultyService;
import com.test_generator.questions.difficulty.domain.Difficulty;
import com.test_generator.questions.difficulty.domain.repository.GetDifficultyRepository;
import com.test_generator.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import com.test_generator.shared.configuration.CustomPage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class GetDifficultyServiceImpl implements GetDifficultyService {

    private final GetDifficultyRepository getDifficultyRepository;

    @Override
    public Difficulty findById(Integer id) throws NotFoundException {
        return getDifficultyRepository.findById(id);
    }

    @Override
    public Difficulty findByName(String name) throws NotFoundException {
        return getDifficultyRepository.findByName(name.toUpperCase(Locale.ROOT));
    }

    @Override
    public CustomPage<Difficulty> findAll(PageRequest pageRequest) {

        return getDifficultyRepository.findAll(pageRequest);
    }
}
