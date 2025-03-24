package com.test_generator.questions.question.domain;

import com.test_generator.questions.difficulty.domain.Difficulty;
import com.test_generator.questions.subjects.subject.domain.Subject;
import com.test_generator.questions.subjects.topic.domain.Topic;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {

    private Integer id;

    private Subject subject;

    private Topic topic;

    private Difficulty difficulty;

    private String question;

    private String choices;

    private String answer;

    private LocalDateTime generatedAt;
}
