package com.test_generator.questions.subjects.topic.infraestructure.repository.impl;

import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.topic.domain.repository.DeleteTopicRepository;
import com.test_generator.questions.subjects.topic.infraestructure.repository.TopicJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.test_generator.shared.constants.Constants.TOPIC_ENTITY;

@Repository
@RequiredArgsConstructor
public class DeleteTopicRepositoryImpl implements DeleteTopicRepository {

    private final TopicJpaRepository topicJpaRepository;

    @Override
    public void deleteById(Integer id) throws NotFoundException {

        topicJpaRepository.deleteById(topicJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(TOPIC_ENTITY, id))
                .getId()
        );
    }
}
