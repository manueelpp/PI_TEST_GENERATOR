package com.test_generator.questions.subjects.topic.infraestructure.controller;

import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.topic.application.DeleteTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.test_generator.shared.constants.Constants.*;

@RestController

@RequestMapping("/topics")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DeleteTopicController {

    private final DeleteTopicService deleteTopicService;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteByid (@PathVariable Integer id) throws NotFoundException {

        deleteTopicService.deleteById(id);

        return ResponseEntity.ok(TOPIC_ENTITY + WITH_ID + "id" + WAS_DELETED_SUCCESSFULLY);
    }
}
