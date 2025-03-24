package com.test_generator.questions.difficulty.infraestructure.repository.impl;


import com.test_generator.questions.difficulty.application.mapper.DifficultyMapper;
import com.test_generator.questions.difficulty.domain.Difficulty;
import com.test_generator.questions.difficulty.domain.repository.GetDifficultyRepository;
import com.test_generator.questions.difficulty.infraestructure.repository.DifficultyJpaRepository;
import com.test_generator.questions.difficulty.infraestructure.repository.entity.DifficultyJpa;
import com.test_generator.shared.configuration.CustomPage;
import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.shared.services.CreateOutputService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.test_generator.shared.constants.Constants.DIFFICULTY_ENTITY;

@Repository
@RequiredArgsConstructor
public class GetDifficultyRepositoryImpl implements GetDifficultyRepository {

    private final DifficultyMapper difficultyMapper;
    private final DifficultyJpaRepository difficultyJpaRepository;

    private final CreateOutputService createOutputService;

    @Override
    public Difficulty findById(Integer id) throws NotFoundException {

        DifficultyJpa difficultyJpa = difficultyJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(DIFFICULTY_ENTITY, id));

        return difficultyMapper.jpaToDomain(difficultyJpa);
    }

    @Override
    public Difficulty findByName(String name) throws NotFoundException {

        DifficultyJpa difficultyJpa = difficultyJpaRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new NotFoundException(DIFFICULTY_ENTITY, name));

        return difficultyMapper.jpaToDomain(difficultyJpa);
    }

    @Override
    public CustomPage<Difficulty> findAll(PageRequest pageRequest) {

        Page<DifficultyJpa> difficultyJpaCustomPage = difficultyJpaRepository.findAll(pageRequest);

        List<Difficulty> difficultyList = difficultyJpaCustomPage
                .getContent()
                .stream()
                .map(difficultyMapper::jpaToDomain)
                .toList();

        return CustomPage.<Difficulty>builder()
                .content(difficultyList)
                .totalPages(difficultyJpaCustomPage.getTotalPages())
                .totalElements(difficultyJpaCustomPage.getTotalElements())
                .currentPage(difficultyJpaCustomPage.getNumber())
                .build();
    }
}
