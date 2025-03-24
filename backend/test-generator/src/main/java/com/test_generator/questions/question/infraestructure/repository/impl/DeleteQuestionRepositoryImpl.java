package com.test_generator.questions.question.infraestructure.repository.impl;

import com.test_generator.questions.question.domain.repository.DeleteQuestionRepository;
import com.test_generator.questions.question.infraestructure.repository.QuestionJpaRepository;
import com.test_generator.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.test_generator.shared.constants.Constants.QUESTION_ENTITY;

@Repository
@RequiredArgsConstructor
public class DeleteQuestionRepositoryImpl implements DeleteQuestionRepository {

    private final QuestionJpaRepository questionJpaRepository;

    @Override
    public void deleteById(Integer id) throws NotFoundException {

        questionJpaRepository.deleteById(questionJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(QUESTION_ENTITY, id))
                .getId()
        );
    }
}
