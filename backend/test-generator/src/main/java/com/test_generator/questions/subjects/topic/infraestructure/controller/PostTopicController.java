package com.test_generator.questions.subjects.topic.infraestructure.controller;

import com.test_generator.questions.subjects.topic.application.PostTopicService;
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
public class PostTopicController {

    private final PostTopicService postTopicService;
    private final TopicMapper topicMapper;

    @PostMapping()
    public ResponseEntity<TopicOutputDto> postTopic (@RequestBody TopicInputDto topicInputDto){

        return ResponseEntity.ok(topicMapper.domainToOutput(
                postTopicService.createTopic(topicInputDto))
        );
    }
}
