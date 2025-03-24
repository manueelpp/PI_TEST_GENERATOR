package com.test_generator.questions.subjects.topic.application.mapper;

import com.test_generator.questions.subjects.topic.domain.Topic;
import com.test_generator.questions.subjects.topic.infraestructure.dtos.input.TopicInputDto;
import com.test_generator.questions.subjects.topic.infraestructure.dtos.output.TopicOutputDto;
import com.test_generator.questions.subjects.topic.infraestructure.repository.entity.TopicJpa;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class TopicMapper {

    // DTOs - Domain
    @Mapping(target = "subject.name", source = "subject")
    @Mapping(target = "id", ignore = true)
    public abstract Topic inputToDomain(TopicInputDto inputDto);

    public abstract TopicOutputDto domainToOutput(Topic question);

    // Domain - JPA
    public abstract Topic jpaToDomain(TopicJpa questionEntity);
    public abstract TopicJpa domainToJpa(Topic question);

    // PUT
    @Mapping(target = "subject.name", source = "subject")
    @Mapping(target = "id", ignore = true)
    public abstract Topic update (@MappingTarget Topic target, TopicInputDto source);
}
