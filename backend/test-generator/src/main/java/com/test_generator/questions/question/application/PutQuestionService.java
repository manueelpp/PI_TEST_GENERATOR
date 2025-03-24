package com.test_generator.questions.question.application;

import com.test_generator.questions.question.domain.Question;
import com.test_generator.questions.question.infraestructure.dtos.input.QuestionInputDto;
import com.test_generator.shared.exceptions.NotFoundException;

public interface PutQuestionService {

    Question updateById(Integer id, QuestionInputDto questionInputDto) throws NotFoundException;
}
