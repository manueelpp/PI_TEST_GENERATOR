package com.test_generator.questions.question.infraestructure.repository.impl;


import com.test_generator.questions.question.application.mapper.QuestionMapper;
import com.test_generator.questions.question.domain.Question;
import com.test_generator.questions.question.domain.repository.PostQuestionRepository;
import com.test_generator.questions.question.infraestructure.repository.QuestionJpaRepository;
import com.test_generator.questions.question.infraestructure.repository.entity.QuestionJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQuestionRepositoryImpl implements PostQuestionRepository {

    private final QuestionJpaRepository questionJpaRepository;
    private final QuestionMapper questionMapper;

    @Override
    public Question createQuestion(Question question) {

        QuestionJpa questionJpa = questionJpaRepository.save(
                questionMapper.domainToJpa(question)
        );

        return questionMapper.jpaToDomain(questionJpa);
    }
}
