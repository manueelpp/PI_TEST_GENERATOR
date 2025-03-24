package com.test_generator.questions.subjects.topic.application.impl;

import com.test_generator.questions.subjects.subject.application.PostSubjectService;
import com.test_generator.questions.subjects.subject.domain.Subject;
import com.test_generator.questions.subjects.topic.application.GetTopicService;
import com.test_generator.questions.subjects.topic.application.PostTopicService;
import com.test_generator.questions.subjects.topic.application.mapper.TopicMapper;
import com.test_generator.questions.subjects.topic.domain.Topic;
import com.test_generator.questions.subjects.topic.domain.repository.PostTopicRepository;
import com.test_generator.questions.subjects.topic.infraestructure.dtos.input.TopicInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;

import static com.test_generator.shared.constants.Constants.TOPIC_MANDATORY_FIELDS;

@Service
@RequiredArgsConstructor
public class PostTopicServiceImpl implements PostTopicService {

    private final PostTopicRepository postTopicRepository;
    private final GetTopicService getTopicService;
    private final TopicMapper topicMapper;

    private final PostSubjectService postSubjectService;

    @Override
    public Topic createTopic(TopicInputDto topicInputDto) {

        validateTopicInput(topicInputDto);

        return getTopicService
                .findByNameAndSubjectName(topicInputDto.getName(), topicInputDto.getSubject())
                .orElseGet(() -> createNewTopic(topicInputDto));
    }

    @Override
    public Topic getOrCreateTopic(String topicName, String subjectName) {

        if (Objects.isNull(topicName) || Objects.isNull(subjectName)) {
            return null;
        }

        return getTopicService.findByNameAndSubjectName(topicName, subjectName)
                .orElseGet(() -> createTopic(
                        new TopicInputDto(
                                topicName.toUpperCase(Locale.ROOT),
                                subjectName.toUpperCase(Locale.ROOT)))
                );
    }

    private void validateTopicInput(TopicInputDto topicInputDto) {

        if (Objects.isNull(topicInputDto.getName()) || Objects.isNull(topicInputDto.getSubject())) {
            throw new IllegalArgumentException(TOPIC_MANDATORY_FIELDS);
        }
    }

    private Topic createNewTopic(TopicInputDto topicInputDto) {

        Topic topic = topicMapper.inputToDomain(topicInputDto);
        Subject subject = postSubjectService.getOrCreateSubject(topicInputDto.getSubject());

        topic.setSubject(subject);
        return postTopicRepository.createTopic(topic);
    }

}
