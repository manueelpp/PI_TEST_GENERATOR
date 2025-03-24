package com.test_generator.chat.services;

import com.test_generator.questions.question.domain.Question;

import java.util.List;

public interface CsvProcessor {

    List<Question> extractQuestions (String csvContent);
}
