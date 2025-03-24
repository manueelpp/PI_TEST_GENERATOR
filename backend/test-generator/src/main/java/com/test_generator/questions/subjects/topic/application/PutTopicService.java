package com.test_generator.questions.subjects.topic.application;

import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.topic.domain.Topic;
import com.test_generator.questions.subjects.topic.infraestructure.dtos.input.TopicInputDto;

public interface PutTopicService {

    Topic updateById(Integer id, TopicInputDto topicInputDto) throws NotFoundException;
}
