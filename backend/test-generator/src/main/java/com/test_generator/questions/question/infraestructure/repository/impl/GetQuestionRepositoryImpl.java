package com.test_generator.questions.question.infraestructure.repository.impl;


import com.test_generator.questions.question.application.mapper.QuestionMapper;
import com.test_generator.questions.question.domain.Question;
import com.test_generator.questions.question.domain.repository.GetQuestionRepository;
import com.test_generator.questions.question.infraestructure.repository.QuestionJpaRepository;
import com.test_generator.questions.question.infraestructure.repository.entity.QuestionJpa;
import com.test_generator.shared.configuration.CustomPage;
import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.shared.services.CreateOutputService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.test_generator.shared.constants.Constants.QUESTION_ENTITY;

@Repository
@RequiredArgsConstructor
public class GetQuestionRepositoryImpl implements GetQuestionRepository {

    private final QuestionMapper questionMapper;
    private final QuestionJpaRepository questionJpaRepository;
    
    private final CreateOutputService createOutputService;
    
    @Override
    public Question findById(Integer id) throws NotFoundException {

        QuestionJpa questionJpa = questionJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(QUESTION_ENTITY, id));

        return questionMapper.jpaToDomain(questionJpa);
    }

    @Override
    public CustomPage<Question> findAll(PageRequest pageRequest) {

        Page<QuestionJpa> questionJpaPage = questionJpaRepository.findAll(pageRequest);

        List<Question> questionList = questionJpaPage
                .getContent()
                .stream()
                .map(questionMapper::jpaToDomain)
                .toList();

        return CustomPage.<Question>builder()
                .content(questionList)
                .totalPages(questionJpaPage.getTotalPages())
                .totalElements(questionJpaPage.getTotalElements())
                .currentPage(questionJpaPage.getNumber())
                .build();
    }

    @Override
    public CustomPage<Question> findByDateAfter(PageRequest pageRequest, LocalDateTime generationDate) {
        
        Page<QuestionJpa> questionJpaPage = questionJpaRepository.findByGeneratedAtAfter(pageRequest, generationDate);

        List<Question> questionList = questionJpaPage
                .getContent()
                .stream()
                .map(questionMapper::jpaToDomain)
                .toList();

        return CustomPage.<Question>builder()
                .content(questionList)
                .totalPages(questionJpaPage.getTotalPages())
                .totalElements(questionJpaPage.getTotalElements())
                .currentPage(questionJpaPage.getNumber())
                .build();
    }
    
}
