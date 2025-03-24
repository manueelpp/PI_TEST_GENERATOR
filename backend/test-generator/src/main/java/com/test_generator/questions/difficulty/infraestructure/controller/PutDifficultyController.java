package com.test_generator.questions.difficulty.infraestructure.controller;

import com.test_generator.questions.difficulty.application.PutDifficultyService;
import com.test_generator.questions.difficulty.application.mapper.DifficultyMapper;
import com.test_generator.questions.difficulty.infraestructure.dtos.input.DifficultyInputDto;
import com.test_generator.questions.difficulty.infraestructure.dtos.output.DifficultyOutputDto;
import com.test_generator.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/difficulties")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PutDifficultyController {

    private final DifficultyMapper difficultyMapper;
    private final PutDifficultyService putDifficultyService;

    @PutMapping("/{id}")
    public ResponseEntity<DifficultyOutputDto> updateDifficultyById(@PathVariable Integer id,
                                                            @RequestBody DifficultyInputDto difficultyInputDto) throws NotFoundException {

        return ResponseEntity.ok(difficultyMapper.domainToOutput(
                putDifficultyService.updateById(id, difficultyInputDto))
        );
    }
}
