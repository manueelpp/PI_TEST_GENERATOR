package com.test_generator.questions.subjects.subject.infraestructure.controller;

import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.subject.application.PutSubjectService;
import com.test_generator.questions.subjects.subject.application.mapper.SubjectMapper;
import com.test_generator.questions.subjects.subject.infraestructure.dtos.input.SubjectInputDto;
import com.test_generator.questions.subjects.subject.infraestructure.dtos.output.SubjectOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/subjects")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PutSubjectController {

    private final SubjectMapper subjectMapper;
    private final PutSubjectService putSubjectService;

    @PutMapping("/{id}")
    public ResponseEntity<SubjectOutputDto> updateSubjectById(@PathVariable Integer id,
                                                            @RequestBody SubjectInputDto subjectInputDto) throws NotFoundException {

        return ResponseEntity.ok(subjectMapper.domainToOutput(
                putSubjectService.updateById(id, subjectInputDto))
        );
    }
}
