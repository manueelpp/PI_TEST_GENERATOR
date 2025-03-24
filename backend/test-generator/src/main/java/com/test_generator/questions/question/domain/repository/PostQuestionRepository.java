package com.test_generator.questions.question.domain.repository;

import com.test_generator.questions.question.domain.Question;

public interface PostQuestionRepository {

    Question createQuestion(Question question);
}
