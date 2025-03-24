package com.test_generator.questions.question.application.impl;

import com.test_generator.questions.difficulty.application.GetDifficultyService;
import com.test_generator.questions.difficulty.domain.Difficulty;
import com.test_generator.questions.question.application.PostQuestionService;
import com.test_generator.questions.question.application.mapper.QuestionMapper;
import com.test_generator.questions.question.domain.Question;
import com.test_generator.questions.question.domain.repository.PostQuestionRepository;
import com.test_generator.questions.question.infraestructure.dtos.input.QuestionInputDto;
import com.test_generator.questions.subjects.subject.application.PostSubjectService;
import com.test_generator.questions.subjects.subject.domain.Subject;
import com.test_generator.questions.subjects.topic.application.PostTopicService;
import com.test_generator.questions.subjects.topic.domain.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostQuestionServiceImpl implements PostQuestionService {

    private final PostQuestionRepository postQuestionRepository;
    private final QuestionMapper questionMapper;

    private final GetDifficultyService getDifficultyService;

    private final PostSubjectService postSubjectService;
    private final PostTopicService postTopicService;

    @Override
    public Question createQuestion(QuestionInputDto questionInputDto) {

        Subject subject = postSubjectService.getOrCreateSubject(questionInputDto.getSubject());
        Topic topic = postTopicService.getOrCreateTopic(questionInputDto.getTopic(), questionInputDto.getSubject());
        Difficulty difficulty = getDifficulty(questionInputDto.getDifficulty());

        Question question = questionMapper.inputToDomain(questionInputDto);
        question.setSubject(subject);
        question.setTopic(topic);
        question.setDifficulty(difficulty);

        return postQuestionRepository.createQuestion(question);
    }

    private Difficulty getDifficulty(String difficultyName) {
        return Objects.nonNull(difficultyName) ? getDifficultyService.findByName(difficultyName.toUpperCase(Locale.ROOT)) : null;
    }

}
