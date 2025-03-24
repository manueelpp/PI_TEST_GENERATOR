package com.test_generator.questions.subjects.topic.infraestructure.repository.impl;


import com.test_generator.shared.configuration.CustomPage;
import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.topic.application.mapper.TopicMapper;
import com.test_generator.questions.subjects.topic.domain.Topic;
import com.test_generator.questions.subjects.topic.domain.repository.GetTopicRepository;
import com.test_generator.questions.subjects.topic.infraestructure.repository.TopicJpaRepository;
import com.test_generator.questions.subjects.topic.infraestructure.repository.entity.TopicJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.test_generator.shared.constants.Constants.TOPIC_ENTITY;

@Repository
@RequiredArgsConstructor
public class GetTopicRepositoryImpl implements GetTopicRepository {

    private final TopicMapper topicMapper;
    private final TopicJpaRepository topicJpaRepository;

    @Override
    public Topic findById(Integer id) throws NotFoundException {

        TopicJpa topicJpa = topicJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(TOPIC_ENTITY, id));

        return topicMapper.jpaToDomain(topicJpa);
    }

    @Override
    public Topic findByName(String name) throws NotFoundException {

        TopicJpa topicJpa = topicJpaRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new NotFoundException(TOPIC_ENTITY, name));

        return topicMapper.jpaToDomain(topicJpa);
    }

    @Override
    public Optional<Topic> findByNameAndSubjectName(String topic, String subject) throws NotFoundException {

        Optional<TopicJpa> topicJpa = topicJpaRepository.findByNameIgnoreCaseAndSubject_NameIgnoreCase(topic, subject);

        return topicJpa.map(topicMapper::jpaToDomain);
    }

    @Override
    public CustomPage<Topic> findAll(PageRequest pageRequest) {

        Page<TopicJpa> topicJpaCustomPage = topicJpaRepository.findAll(pageRequest);

        List<Topic> topicList = topicJpaCustomPage.getContent()
                .stream()
                .map(topicMapper::jpaToDomain)
                .toList();

        return CustomPage.<Topic>builder()
                .content(topicList)
                .totalPages(topicJpaCustomPage.getTotalPages())
                .totalElements(topicJpaCustomPage.getTotalElements())
                .currentPage(topicJpaCustomPage.getNumber())
                .build();
    }

    @Override
    public CustomPage<Topic> findAllBySubjectId(PageRequest pageRequest, Integer subjectId) {

        Page<TopicJpa> topicJpaCustomPage = topicJpaRepository.findAllBySubject_Id(pageRequest, subjectId);

        List<Topic> topicList = topicJpaCustomPage.getContent()
                .stream()
                .map(topicMapper::jpaToDomain)
                .toList();

        return CustomPage.<Topic>builder()
                .content(topicList)
                .totalPages(topicJpaCustomPage.getTotalPages())
                .totalElements(topicJpaCustomPage.getTotalElements())
                .currentPage(topicJpaCustomPage.getNumber())
                .build();
    }
}
