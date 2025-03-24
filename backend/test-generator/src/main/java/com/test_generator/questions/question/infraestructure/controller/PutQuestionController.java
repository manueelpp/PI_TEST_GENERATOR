package com.test_generator.questions.question.infraestructure.controller;

import com.test_generator.questions.question.application.PutQuestionService;
import com.test_generator.questions.question.application.mapper.QuestionMapper;
import com.test_generator.questions.question.infraestructure.dtos.input.QuestionInputDto;
import com.test_generator.questions.question.infraestructure.dtos.output.QuestionOutputDto;
import com.test_generator.shared.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/questions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PutQuestionController {

    private final QuestionMapper questionMapper;
    private final PutQuestionService putQuestionService;

    @PutMapping("/{id}")
    public ResponseEntity<QuestionOutputDto> updateQuestionById(@PathVariable Integer id,
                                                            @RequestBody QuestionInputDto questionInputDto) throws NotFoundException {

        return ResponseEntity.ok(questionMapper.domainToOutput(
                putQuestionService.updateById(id, questionInputDto))
        );
    }
}
