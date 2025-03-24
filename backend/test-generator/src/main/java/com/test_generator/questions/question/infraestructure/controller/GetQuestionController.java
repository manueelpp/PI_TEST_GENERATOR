package com.test_generator.questions.question.infraestructure.controller;


import com.test_generator.questions.question.application.GetQuestionService;
import com.test_generator.questions.question.application.mapper.QuestionMapper;
import com.test_generator.questions.question.domain.Question;
import com.test_generator.questions.question.infraestructure.dtos.output.QuestionOutputDto;
import com.test_generator.shared.configuration.CustomPage;
import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.shared.services.CreateOutputService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController

@RequestMapping("/questions")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class GetQuestionController {

    private final QuestionMapper questionMapper;
    private final GetQuestionService getQuestionService;

    private final CreateOutputService createOutputService;

    @GetMapping("/{id}")
    public ResponseEntity<QuestionOutputDto> findById(@PathVariable Integer id) throws NotFoundException {

        QuestionOutputDto questionOutputDto = createOutputService.entityToOutput(
                getQuestionService.findById(id),
                questionMapper::domainToOutput
        );

        return ResponseEntity.ok(questionOutputDto);
    }

    @GetMapping()
    public ResponseEntity<CustomPage<QuestionOutputDto>> findAll(@RequestParam(defaultValue = "0") Integer page,
                                                                 @RequestParam(defaultValue = "100") Integer size,
                                                                 @RequestParam(required = false) String sortField,
                                                                 @RequestParam(required = false) SortDirection sortDirection){

        CustomPage<Question> customPageDto = getQuestionService.findAll(
                createOutputService.createSortedPageRequest(page, size, sortField, sortDirection)
        );

        CustomPage<QuestionOutputDto> outputDtoCustomPage = createOutputService.pageToOutput(
                customPageDto,
                questionMapper::domainToOutput
        );

        return ResponseEntity.ok(outputDtoCustomPage);
    }

    @GetMapping("/generationDate")
    public ResponseEntity<CustomPage<QuestionOutputDto>> findByGenerationDate(@RequestParam(defaultValue = "0") Integer page,
                                                                              @RequestParam(defaultValue = "20") Integer size,
                                                                              @RequestParam(required = false) String sortField,
                                                                              @RequestParam(required = false) SortDirection sortDirection,
                                                                              @RequestParam(required = false) LocalDateTime generationDate){

        CustomPage<Question> customPageDto = getQuestionService.findByDateAfter(
                createOutputService.createSortedPageRequest(page, size, sortField, sortDirection),
                generationDate
        );

        CustomPage<QuestionOutputDto> outputDtoCustomPage = createOutputService.pageToOutput(
                customPageDto,
                questionMapper::domainToOutput
        );

        return ResponseEntity.ok(outputDtoCustomPage);
    }
}
