package com.test_generator.questions.question.application.impl;

import com.test_generator.questions.difficulty.application.GetDifficultyService;
import com.test_generator.questions.difficulty.domain.Difficulty;
import com.test_generator.questions.question.application.GetQuestionService;
import com.test_generator.questions.question.application.PutQuestionService;
import com.test_generator.questions.question.application.mapper.QuestionMapper;
import com.test_generator.questions.question.domain.Question;
import com.test_generator.questions.question.domain.repository.PutQuestionRepository;
import com.test_generator.questions.question.infraestructure.dtos.input.QuestionInputDto;
import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.subject.application.GetSubjectService;
import com.test_generator.questions.subjects.subject.application.PostSubjectService;
import com.test_generator.questions.subjects.subject.domain.Subject;
import com.test_generator.questions.subjects.subject.infraestructure.dtos.input.SubjectInputDto;
import com.test_generator.questions.subjects.topic.application.GetTopicService;
import com.test_generator.questions.subjects.topic.application.PostTopicService;
import com.test_generator.questions.subjects.topic.domain.Topic;
import com.test_generator.questions.subjects.topic.infraestructure.dtos.input.TopicInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PutQuestionServiceImpl implements PutQuestionService {

    private final QuestionMapper questionMapper;
    private final PutQuestionRepository putQuestionRepository;
    private final GetQuestionService getQuestionService;

    private final GetSubjectService getSubjectService;
    private final GetTopicService getTopicService;
    private final GetDifficultyService getDifficultyService;

    private final PostSubjectService postSubjectService;
    private final PostTopicService postTopicService;

    @Override
    public Question updateById(Integer id, QuestionInputDto questionInputDto) throws NotFoundException {

        Question question = getQuestionService.findById(id);

        Question updatedQuestion = questionMapper.update(question, questionInputDto);

        Subject subject = getOrCreateSubject(questionInputDto.getSubject());
        Topic topic = getOrCreateTopic(questionInputDto.getTopic(), questionInputDto.getSubject());
        Difficulty difficulty = getDifficulty(questionInputDto.getDifficulty());

        if (Objects.nonNull(subject))
            updatedQuestion.setSubject(subject);

        if (Objects.nonNull(topic))
            updatedQuestion.setTopic(topic);

        if (Objects.nonNull(difficulty))
            updatedQuestion.setDifficulty(difficulty);

        return putQuestionRepository.updateQuestion(updatedQuestion);
    }

    private Subject getOrCreateSubject(String subjectName) {

        if (Objects.isNull(subjectName)) {
            return null;
        }

        return getSubjectService.findByName(subjectName)
                .orElseGet(() -> postSubjectService.createSubject(
                        new SubjectInputDto(subjectName))
                );
    }

    private Topic getOrCreateTopic(String topicName, String subjectName) {

        if (Objects.isNull(topicName) || Objects.isNull(subjectName)) {
            return null;
        }

        return getTopicService.findByNameAndSubjectName(topicName, subjectName)
                .orElseGet(() -> postTopicService.createTopic(
                        new TopicInputDto(
                                topicName.toUpperCase(Locale.ROOT),
                                subjectName.toUpperCase(Locale.ROOT)))
                );
    }

    private Difficulty getDifficulty(String difficultyName) {
        return Objects.nonNull(difficultyName) ? getDifficultyService.findByName(difficultyName.toUpperCase(Locale.ROOT)) : null;
    }
}
