package com.test_generator.questions.question.application;

import com.test_generator.questions.question.domain.Question;
import com.test_generator.questions.question.infraestructure.dtos.input.QuestionInputDto;

public interface PostQuestionService {

    Question createQuestion(QuestionInputDto questionInputDto);
}
