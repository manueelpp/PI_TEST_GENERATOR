package com.test_generator.questions.subjects.subject.application.mapper;

import com.test_generator.questions.subjects.subject.domain.Subject;
import com.test_generator.questions.subjects.subject.infraestructure.dtos.input.SubjectInputDto;
import com.test_generator.questions.subjects.subject.infraestructure.dtos.output.SubjectOutputDto;
import com.test_generator.questions.subjects.subject.infraestructure.repository.entity.SubjectJpa;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class SubjectMapper {

    // DTOs - Domain
    @Mapping(target = "id", ignore = true)
    public abstract Subject inputToDomain(SubjectInputDto inputDto);
    public abstract SubjectOutputDto domainToOutput(Subject question);

    // Domain - JPA
    public abstract Subject jpaToDomain(SubjectJpa questionEntity);
    public abstract SubjectJpa domainToJpa(Subject question);

    // PUT
    @Mapping(target = "id", ignore = true)
    public abstract Subject update (@MappingTarget Subject target, SubjectInputDto source);
}
