package com.test_generator.questions.subjects.topic.application.impl;

import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.subject.application.PostSubjectService;
import com.test_generator.questions.subjects.topic.application.GetTopicService;
import com.test_generator.questions.subjects.topic.application.PutTopicService;
import com.test_generator.questions.subjects.topic.application.mapper.TopicMapper;
import com.test_generator.questions.subjects.topic.domain.Topic;
import com.test_generator.questions.subjects.topic.domain.repository.PutTopicRepository;
import com.test_generator.questions.subjects.topic.infraestructure.dtos.input.TopicInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PutTopicServiceImpl implements PutTopicService {

    private final TopicMapper topicMapper;
    private final PutTopicRepository putTopicRepository;
    private final GetTopicService getTopicService;

    private final PostSubjectService postSubjectService;

    @Override
    public Topic updateById(Integer id, TopicInputDto topicInputDto) throws NotFoundException {

        Topic topic = getTopicService.findById(id);

        Topic updatedTopic = topicMapper.update(topic, topicInputDto);
        updatedTopic.setSubject(
                postSubjectService.getOrCreateSubject(
                        topicInputDto.getSubject()
                )
        );

        return putTopicRepository.updateTopic(updatedTopic);
    }
}
