package com.test_generator.questions.subjects.topic.application;

import com.test_generator.shared.exceptions.NotFoundException;

public interface DeleteTopicService {

    void deleteById(Integer id) throws NotFoundException;
}
