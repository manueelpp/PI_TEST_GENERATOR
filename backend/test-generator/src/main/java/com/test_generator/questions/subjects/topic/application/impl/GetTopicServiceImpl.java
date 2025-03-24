package com.test_generator.questions.subjects.topic.application.impl;

import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.topic.application.GetTopicService;
import com.test_generator.questions.subjects.topic.domain.Topic;
import com.test_generator.questions.subjects.topic.domain.repository.GetTopicRepository;
import lombok.RequiredArgsConstructor;
import com.test_generator.shared.configuration.CustomPage;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetTopicServiceImpl implements GetTopicService {

    private final GetTopicRepository getTopicRepository;

    @Override
    public Topic findById(Integer id) throws NotFoundException {
        return getTopicRepository.findById(id);
    }

    @Override
    public Topic findByName(String name) throws NotFoundException {
        return getTopicRepository.findByName(name.toUpperCase(Locale.ROOT));
    }

    @Override
    public Optional<Topic> findByNameAndSubjectName(String topic, String subject) throws NotFoundException {
        return getTopicRepository.findByNameAndSubjectName(topic, subject);
    }

    @Override
    public CustomPage<Topic> findAll(PageRequest pageRequest) {

        return getTopicRepository.findAll(pageRequest);
    }

    @Override
    public CustomPage<Topic> findAllBySubjectId(PageRequest pageRequest, Integer subjectId) {

        return getTopicRepository.findAllBySubjectId(pageRequest, subjectId);
    }
}
