package com.test_generator.questions.subjects.topic.application.impl;

import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.topic.application.DeleteTopicService;
import com.test_generator.questions.subjects.topic.domain.repository.DeleteTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTopicServiceImpl implements DeleteTopicService {

    private final DeleteTopicRepository deleteTopicRepository;

    @Override
    public void deleteById(Integer id) throws NotFoundException {

        deleteTopicRepository.deleteById(id);
    }
}
