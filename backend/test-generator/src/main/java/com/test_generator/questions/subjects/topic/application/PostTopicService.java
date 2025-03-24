package com.test_generator.questions.subjects.topic.application;

import com.test_generator.questions.subjects.topic.domain.Topic;
import com.test_generator.questions.subjects.topic.infraestructure.dtos.input.TopicInputDto;

public interface PostTopicService {

    Topic createTopic(TopicInputDto topicInputDto);

    Topic getOrCreateTopic(String topicName, String subjectName);
}
