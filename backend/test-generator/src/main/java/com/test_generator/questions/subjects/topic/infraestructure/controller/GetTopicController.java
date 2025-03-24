package com.test_generator.questions.subjects.topic.infraestructure.controller;


import com.test_generator.shared.configuration.CustomPage;
import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.shared.services.CreateOutputService;
import com.test_generator.questions.subjects.topic.application.GetTopicService;
import com.test_generator.questions.subjects.topic.application.mapper.TopicMapper;
import com.test_generator.questions.subjects.topic.domain.Topic;
import com.test_generator.questions.subjects.topic.infraestructure.dtos.output.TopicOutputDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/topics")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class GetTopicController {

    private final TopicMapper topicMapper;
    private final GetTopicService getTopicService;
    
    private final CreateOutputService createOutputService;

    @GetMapping("/{id}")
    public ResponseEntity<TopicOutputDto> findById(@PathVariable Integer id) throws NotFoundException {

        TopicOutputDto topicOutputDto = createOutputService.entityToOutput(
                getTopicService.findById(id),
                topicMapper::domainToOutput
        );

        return ResponseEntity.ok(topicOutputDto);
    }

    @GetMapping("/{name}")
    public ResponseEntity<TopicOutputDto> findByName(@PathVariable String name) throws NotFoundException {

        TopicOutputDto topicOutputDto = createOutputService.entityToOutput(
                getTopicService.findByName(name),
                topicMapper::domainToOutput
        );

        return ResponseEntity.ok(topicOutputDto);
    }

    @GetMapping()
    public ResponseEntity<CustomPage<TopicOutputDto>> findAll(@RequestParam(defaultValue = "0") Integer page,
                                                               @RequestParam(defaultValue = "100") Integer size,
                                                               @RequestParam(required = false) String sortField,
                                                               @RequestParam(required = false) SortDirection sortDirection){

        CustomPage<Topic> customPageDto = getTopicService.findAll(
                createOutputService.createSortedPageRequest(page, size, sortField, sortDirection)
        );

        CustomPage<TopicOutputDto> outputDtoCustomPage = createOutputService.pageToOutput(
                customPageDto,
                topicMapper::domainToOutput
        );

        return ResponseEntity.ok(outputDtoCustomPage);
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<CustomPage<TopicOutputDto>> findAllBySubject(@RequestParam(defaultValue = "0") Integer page,
                                                                       @RequestParam(defaultValue = "100") Integer size,
                                                                       @RequestParam(required = false) String sortField,
                                                                       @RequestParam(required = false) SortDirection sortDirection,
                                                                       @PathVariable Integer subjectId){

        CustomPage<Topic> customPageDto = getTopicService.findAllBySubjectId(
                createOutputService.createSortedPageRequest(page, size, sortField, sortDirection),
                subjectId
        );

        CustomPage<TopicOutputDto> outputDtoCustomPage = createOutputService.pageToOutput(
                customPageDto,
                topicMapper::domainToOutput
        );

        return ResponseEntity.ok(outputDtoCustomPage);
    }
}
