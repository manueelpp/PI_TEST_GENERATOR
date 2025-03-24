package com.test_generator.questions.subjects.subject.application.impl;

import com.test_generator.questions.subjects.subject.application.GetSubjectService;
import com.test_generator.questions.subjects.subject.application.PostSubjectService;
import com.test_generator.questions.subjects.subject.application.mapper.SubjectMapper;
import com.test_generator.questions.subjects.subject.domain.Subject;
import com.test_generator.questions.subjects.subject.domain.repository.PostSubjectRepository;
import com.test_generator.questions.subjects.subject.infraestructure.dtos.input.SubjectInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostSubjectServiceImpl implements PostSubjectService {

    private final PostSubjectRepository postSubjectRepository;
    private final GetSubjectService getSubjectService;
    private final SubjectMapper subjectMapper;

    @Override
    public Subject createSubject(SubjectInputDto subjectInputDto) {

        return postSubjectRepository.createSubject(
                subjectMapper.inputToDomain(subjectInputDto)
        );
    }

    @Override
    public Subject getOrCreateSubject(String subjectName) {

        if (Objects.isNull(subjectName)) {
            return null;
        }

        return getSubjectService.findByName(subjectName)
                .orElseGet(() -> createSubject(
                        new SubjectInputDto(subjectName.toUpperCase(Locale.ROOT)))
                );
    }
}
