package com.test_generator.questions.question.infraestructure.repository.impl;

import com.test_generator.questions.question.application.mapper.QuestionMapper;
import com.test_generator.questions.question.domain.Question;
import com.test_generator.questions.question.domain.repository.PutQuestionRepository;
import com.test_generator.questions.question.infraestructure.repository.QuestionJpaRepository;
import com.test_generator.questions.question.infraestructure.repository.entity.QuestionJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PutQuestionRepositoryImpl implements PutQuestionRepository {

    private final QuestionMapper questionMapper;
    private final QuestionJpaRepository questionJpaRepository;
    @Override
    public Question updateQuestion(Question question) {

        QuestionJpa questionJpa= questionJpaRepository.save(
                questionMapper.domainToJpa(question)
        );

        return questionMapper.jpaToDomain(questionJpa);
    }
}
