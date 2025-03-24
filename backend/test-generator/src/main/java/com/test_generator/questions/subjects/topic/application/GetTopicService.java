package com.test_generator.questions.subjects.topic.application;

import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.topic.domain.Topic;
import com.test_generator.shared.configuration.CustomPage;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface GetTopicService {

    Topic findById(Integer id) throws NotFoundException;

    Topic findByName(String name) throws NotFoundException;

    Optional<Topic> findByNameAndSubjectName(String topic, String subject) throws NotFoundException;

    CustomPage<Topic> findAll(PageRequest pageRequest);

    CustomPage<Topic> findAllBySubjectId(PageRequest pageRequest, Integer subjectId);
}
