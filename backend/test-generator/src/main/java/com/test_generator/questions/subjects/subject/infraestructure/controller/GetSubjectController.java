package com.test_generator.questions.subjects.subject.infraestructure.controller;

import com.test_generator.shared.configuration.CustomPage;
import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.shared.services.CreateOutputService;
import com.test_generator.questions.subjects.subject.application.GetSubjectService;
import com.test_generator.questions.subjects.subject.application.mapper.SubjectMapper;
import com.test_generator.questions.subjects.subject.domain.Subject;
import com.test_generator.questions.subjects.subject.infraestructure.dtos.output.SubjectOutputDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/subjects")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class GetSubjectController {

    private final SubjectMapper subjectMapper;
    private final GetSubjectService getSubjectService;

    private final CreateOutputService createOutputService;

    @GetMapping("/{id}")
    public ResponseEntity<SubjectOutputDto> findById(@PathVariable Integer id) throws NotFoundException {

        SubjectOutputDto subjectOutputDto = createOutputService.entityToOutput(
                getSubjectService.findById(id),
                subjectMapper::domainToOutput
        );

        return ResponseEntity.ok(subjectOutputDto);
    }

    @GetMapping("/{name}")
    public ResponseEntity<SubjectOutputDto> findByName(@PathVariable String name) throws NotFoundException {

        SubjectOutputDto subjectOutputDto = createOutputService.entityToOutput(
                getSubjectService.findByName(name),
                subjectMapper::domainToOutput
        );

        return ResponseEntity.ok(subjectOutputDto);
    }

    @GetMapping()
    public ResponseEntity<CustomPage<SubjectOutputDto>> findAll(@RequestParam(defaultValue = "0") Integer page,
                                                               @RequestParam(defaultValue = "100") Integer size,
                                                               @RequestParam(required = false) String sortField,
                                                               @RequestParam(required = false) SortDirection sortDirection){

        CustomPage<Subject> customPageDto = getSubjectService.findAll(
                createOutputService.createSortedPageRequest(page, size, sortField, sortDirection)
        );

        CustomPage<SubjectOutputDto> outputDtoCustomPage = createOutputService.pageToOutput(
                customPageDto,
                subjectMapper::domainToOutput
        );

        return ResponseEntity.ok(outputDtoCustomPage);
    }
}
