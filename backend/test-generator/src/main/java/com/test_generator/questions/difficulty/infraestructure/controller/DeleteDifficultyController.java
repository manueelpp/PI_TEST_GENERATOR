package com.test_generator.questions.difficulty.infraestructure.controller;

import com.test_generator.questions.difficulty.application.DeleteDifficultyService;
import com.test_generator.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.test_generator.shared.constants.Constants.*;

@RestController

@RequestMapping("/difficulties")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DeleteDifficultyController {

    private final DeleteDifficultyService deleteDifficultyService;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteByid (@PathVariable Integer id) throws NotFoundException {

        deleteDifficultyService.deleteById(id);

        return ResponseEntity.ok(DIFFICULTY_ENTITY + WITH_ID + id + WAS_DELETED_SUCCESSFULLY);
    }
}
