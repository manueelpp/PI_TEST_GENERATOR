package com.test_generator.questions.question.infraestructure.controller;

import com.test_generator.questions.question.application.DeleteQuestionService;
import com.test_generator.shared.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.test_generator.shared.constants.Constants.*;

@RestController

@RequestMapping("/questions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DeleteQuestionController {

    private final DeleteQuestionService deleteQuestionService;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteByid (@PathVariable Integer id) throws NotFoundException {

        deleteQuestionService.deleteById(id);

        return ResponseEntity.ok(QUESTION_ENTITY + WITH_ID + id + WAS_DELETED_SUCCESSFULLY);
    }
}
