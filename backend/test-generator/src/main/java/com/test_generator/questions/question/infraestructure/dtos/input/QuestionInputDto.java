package com.test_generator.questions.question.infraestructure.dtos.input;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionInputDto {

    private String subject;

    private String topic;

    private String difficulty;

    private String question;

    private List<String> choices;

    private String answer;
}
