package com.test_generator.questions.difficulty.infraestructure.controller;

import com.test_generator.questions.difficulty.application.GetDifficultyService;
import com.test_generator.questions.difficulty.application.mapper.DifficultyMapper;
import com.test_generator.questions.difficulty.domain.Difficulty;
import com.test_generator.questions.difficulty.infraestructure.dtos.output.DifficultyOutputDto;
import com.test_generator.shared.configuration.CustomPage;
import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.shared.services.CreateOutputService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/difficulties")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class GetDifficultyController {

    private final DifficultyMapper difficultyMapper;
    private final GetDifficultyService getDifficultyService;

    private final CreateOutputService createOutputService;

    @GetMapping("/{id}")
    public ResponseEntity<DifficultyOutputDto> findById(@PathVariable Integer id) throws NotFoundException {

        DifficultyOutputDto difficultyOutputDto = createOutputService.entityToOutput(
                getDifficultyService.findById(id),
                difficultyMapper::domainToOutput
        );

        return ResponseEntity.ok(difficultyOutputDto);
    }

    @GetMapping()
    public ResponseEntity<CustomPage<DifficultyOutputDto>> findAll(@RequestParam(defaultValue = "0") Integer page,
                                                                   @RequestParam(defaultValue = "100") Integer size,
                                                                   @RequestParam(required = false) String sortField,
                                                                   @RequestParam(required = false) SortDirection sortDirection){

        CustomPage<Difficulty> customPageDto = getDifficultyService.findAll(
                createOutputService.createSortedPageRequest(page, size, sortField, sortDirection)
        );

        CustomPage<DifficultyOutputDto> outputDtoCustomPage = createOutputService.pageToOutput(
                customPageDto,
                difficultyMapper::domainToOutput
        );

        return ResponseEntity.ok(outputDtoCustomPage);
    }
}
