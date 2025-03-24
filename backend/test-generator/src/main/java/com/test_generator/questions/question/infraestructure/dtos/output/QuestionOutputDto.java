package com.test_generator.questions.question.infraestructure.dtos.output;

import com.test_generator.questions.difficulty.infraestructure.dtos.output.DifficultyOutputDto;
import com.test_generator.questions.subjects.subject.infraestructure.dtos.output.SubjectOutputDto;
import com.test_generator.questions.subjects.topic.infraestructure.dtos.output.TopicOutputDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionOutputDto {

    private Integer id;

    private SubjectOutputDto subject;

    private TopicOutputDto topic;

    private DifficultyOutputDto difficulty;

    private String question;

    private List<String> choices;

    private String answer;

    private LocalDateTime generatedAt;
}
