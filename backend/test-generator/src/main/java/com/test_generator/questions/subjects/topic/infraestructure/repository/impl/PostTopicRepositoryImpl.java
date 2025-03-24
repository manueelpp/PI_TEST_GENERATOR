package com.test_generator.questions.subjects.topic.infraestructure.repository.impl;


import com.test_generator.questions.subjects.topic.application.mapper.TopicMapper;
import com.test_generator.questions.subjects.topic.domain.Topic;
import com.test_generator.questions.subjects.topic.domain.repository.PostTopicRepository;
import com.test_generator.questions.subjects.topic.infraestructure.repository.TopicJpaRepository;
import com.test_generator.questions.subjects.topic.infraestructure.repository.entity.TopicJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostTopicRepositoryImpl implements PostTopicRepository {

    private final TopicJpaRepository topicJpaRepository;
    private final TopicMapper topicMapper;

    @Override
    public Topic createTopic(Topic topic) {

        TopicJpa topicJpa = topicJpaRepository.save(
                topicMapper.domainToJpa(topic)
        );

        return topicMapper.jpaToDomain(topicJpa);
    }
}
