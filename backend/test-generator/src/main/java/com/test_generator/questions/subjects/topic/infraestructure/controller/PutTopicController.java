package com.test_generator.questions.subjects.topic.infraestructure.controller;

import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.topic.application.PutTopicService;
import com.test_generator.questions.subjects.topic.application.mapper.TopicMapper;
import com.test_generator.questions.subjects.topic.infraestructure.dtos.input.TopicInputDto;
import com.test_generator.questions.subjects.topic.infraestructure.dtos.output.TopicOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/topics")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PutTopicController {

    private final TopicMapper topicMapper;
    private final PutTopicService putTopicService;

    @PutMapping("/{id}")
    public ResponseEntity<TopicOutputDto> updateTopicById(@PathVariable Integer id,
                                                            @RequestBody TopicInputDto topicInputDto) throws NotFoundException {

        return ResponseEntity.ok(topicMapper.domainToOutput(
                putTopicService.updateById(id, topicInputDto))
        );
    }
}
