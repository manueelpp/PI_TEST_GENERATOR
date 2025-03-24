package com.test_generator.questions.question.application.impl;

import com.test_generator.questions.question.application.DeleteQuestionService;
import com.test_generator.questions.question.domain.repository.DeleteQuestionRepository;
import com.test_generator.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteQuestionServiceImpl implements DeleteQuestionService {

    private final DeleteQuestionRepository deleteQuestionRepository;

    @Override
    public void deleteById(Integer id) throws NotFoundException {

        deleteQuestionRepository.deleteById(id);
    }
}
