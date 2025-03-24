package com.test_generator.questions.difficulty.infraestructure.controller;

import com.test_generator.questions.difficulty.application.PostDifficultyService;
import com.test_generator.questions.difficulty.application.mapper.DifficultyMapper;
import com.test_generator.questions.difficulty.infraestructure.dtos.input.DifficultyInputDto;
import com.test_generator.questions.difficulty.infraestructure.dtos.output.DifficultyOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/difficulties")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PostDifficultyController {

    private final PostDifficultyService postDifficultyService;
    private final DifficultyMapper difficultyMapper;

    @PostMapping()
    public ResponseEntity<DifficultyOutputDto> postDifficulty (@RequestBody DifficultyInputDto difficultyInputDto){

        return ResponseEntity.ok(difficultyMapper.domainToOutput(
                postDifficultyService.createDifficulty(difficultyInputDto))
        );
    }
}
