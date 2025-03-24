package com.test_generator.questions.subjects.topic.infraestructure.repository.impl;

import com.test_generator.questions.subjects.topic.application.mapper.TopicMapper;
import com.test_generator.questions.subjects.topic.domain.Topic;
import com.test_generator.questions.subjects.topic.domain.repository.PutTopicRepository;
import com.test_generator.questions.subjects.topic.infraestructure.repository.TopicJpaRepository;
import com.test_generator.questions.subjects.topic.infraestructure.repository.entity.TopicJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PutTopicRepositoryImpl implements PutTopicRepository {

    private final TopicMapper topicMapper;
    private final TopicJpaRepository topicJpaRepository;

    @Override
    public Topic updateTopic(Topic topic) {

        TopicJpa topicJpa= topicJpaRepository.save(
                topicMapper.domainToJpa(topic)
        );

        return topicMapper.jpaToDomain(topicJpa);
    }
}
