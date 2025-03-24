package com.test_generator.questions.question.infraestructure.controller;

import com.test_generator.questions.question.application.PostQuestionService;
import com.test_generator.questions.question.application.mapper.QuestionMapper;
import com.test_generator.questions.question.infraestructure.dtos.input.QuestionInputDto;
import com.test_generator.questions.question.infraestructure.dtos.output.QuestionOutputDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/questions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PostQuestionController {

    private final PostQuestionService postQuestionService;
    private final QuestionMapper questionMapper;

    @PostMapping()
    public ResponseEntity<QuestionOutputDto> postQuestion (@RequestBody QuestionInputDto questionInputDto){

        return ResponseEntity.ok(questionMapper.domainToOutput(
                postQuestionService.createQuestion(questionInputDto))
        );
    }
}
