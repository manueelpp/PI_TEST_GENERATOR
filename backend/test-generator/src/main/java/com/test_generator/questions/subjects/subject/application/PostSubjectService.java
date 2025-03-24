package com.test_generator.questions.subjects.subject.application;

import com.test_generator.questions.subjects.subject.domain.Subject;
import com.test_generator.questions.subjects.subject.infraestructure.dtos.input.SubjectInputDto;

public interface PostSubjectService {

    Subject createSubject(SubjectInputDto subjectInputDto);

    Subject getOrCreateSubject(String subjectName);
}
