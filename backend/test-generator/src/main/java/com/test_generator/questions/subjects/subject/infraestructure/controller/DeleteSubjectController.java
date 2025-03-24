package com.test_generator.questions.subjects.subject.infraestructure.controller;

import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.subject.application.DeleteSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.test_generator.shared.constants.Constants.*;

@RestController

@RequestMapping("/subjects")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DeleteSubjectController {

    private final DeleteSubjectService deleteSubjectService;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteByid (@PathVariable Integer id) throws NotFoundException {

        deleteSubjectService.deleteById(id);

        return ResponseEntity.ok(SUBJECT_ENTITY + WITH_ID + "id" + WAS_DELETED_SUCCESSFULLY);
    }
}
