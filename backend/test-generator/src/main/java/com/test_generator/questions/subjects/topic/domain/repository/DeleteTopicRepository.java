package com.test_generator.questions.subjects.topic.domain.repository;

import com.test_generator.shared.exceptions.NotFoundException;

public interface DeleteTopicRepository {

    void deleteById(Integer id) throws NotFoundException;
}
