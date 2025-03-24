package com.test_generator.questions.difficulty.application.mapper;

import com.test_generator.questions.difficulty.domain.Difficulty;
import com.test_generator.questions.difficulty.infraestructure.dtos.input.DifficultyInputDto;
import com.test_generator.questions.difficulty.infraestructure.dtos.output.DifficultyOutputDto;
import com.test_generator.questions.difficulty.infraestructure.repository.entity.DifficultyJpa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class DifficultyMapper {

    // DTOs - Domain
    @Mapping(target = "id", ignore = true)
    public abstract Difficulty inputToDomain(DifficultyInputDto inputDto);
    public abstract DifficultyOutputDto domainToOutput(Difficulty question);

    // Domain - JPA
    public abstract Difficulty jpaToDomain(DifficultyJpa questionEntity);
    public abstract DifficultyJpa domainToJpa(Difficulty question);

    // PUT
    @Mapping(target = "id", ignore = true)
    public abstract Difficulty update (@MappingTarget Difficulty target, DifficultyInputDto source);
}
