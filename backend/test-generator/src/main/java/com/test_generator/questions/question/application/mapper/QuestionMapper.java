package com.test_generator.questions.question.application.mapper;

import com.test_generator.questions.question.domain.Question;
import com.test_generator.questions.question.infraestructure.dtos.input.QuestionInputDto;
import com.test_generator.questions.question.infraestructure.dtos.output.QuestionOutputDto;
import com.test_generator.questions.question.infraestructure.repository.entity.QuestionJpa;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class QuestionMapper {

    // DTOs - Domain
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "generatedAt", ignore = true)
    @Mapping(target = "choices", ignore = true)
    @Mapping(target = "subject.name", source = "subject")
    @Mapping(target = "topic.name", source = "topic")
    @Mapping(target = "difficulty.name", source = "difficulty")
    public abstract Question inputToDomain(QuestionInputDto inputDto);

    @Mapping(target = "choices", ignore = true)
    public abstract QuestionOutputDto domainToOutput(Question question);

    // Domain - JPA
    public abstract Question jpaToDomain(QuestionJpa questionEntity);

    public abstract QuestionJpa domainToJpa(Question question);

    // PUT
    @Mapping(target = "subject.name", source = "subject")
    @Mapping(target = "topic.name", source = "topic")
    @Mapping(target = "difficulty.name", source = "difficulty")
    @Mapping(target = "choices", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "generatedAt", ignore = true)
    public abstract Question update (@MappingTarget Question target, QuestionInputDto source);

    @AfterMapping
    protected void afterMapping(@MappingTarget QuestionOutputDto questionOutputDto, Question question) {

        if (question.getChoices() != null && !question.getChoices().isEmpty()) {
            List<String> choicesList = Arrays.asList(question.getChoices().split("\\|\\|"));
            questionOutputDto.setChoices(choicesList);
        }
    }
    @AfterMapping
    protected void afterMapping(@MappingTarget Question question, QuestionInputDto questionInputDto) {

        question.setGeneratedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        String choices = questionInputDto.getChoices().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.joining("||"));

        question.setChoices(choices);
    }
}
