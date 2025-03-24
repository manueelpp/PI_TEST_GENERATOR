package com.test_generator.questions.subjects.subject.infraestructure.controller;

import com.test_generator.questions.subjects.subject.application.PostSubjectService;
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
public class PostSubjectController {

    private final PostSubjectService postSubjectService;
    private final SubjectMapper subjectMapper;

    @PostMapping()
    public ResponseEntity<SubjectOutputDto> postSubject (@RequestBody SubjectInputDto subjectInputDto){

        return ResponseEntity.ok(subjectMapper.domainToOutput(
                postSubjectService.createSubject(subjectInputDto))
        );
    }
}
