package com.test_generator.questions.question.application;


import com.test_generator.questions.question.domain.Question;
import com.test_generator.shared.configuration.CustomPage;
import com.test_generator.shared.exceptions.NotFoundException;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

public interface GetQuestionService {

    Question findById(Integer id) throws NotFoundException;

    CustomPage<Question> findAll(PageRequest pageRequest);

    CustomPage<Question> findByDateAfter(PageRequest pageRequest, LocalDateTime generationDate);
}
