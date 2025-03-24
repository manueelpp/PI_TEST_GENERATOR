package com.test_generator.questions.subjects.subject.application;

import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.subject.domain.Subject;
import com.test_generator.questions.subjects.subject.infraestructure.dtos.input.SubjectInputDto;

public interface PutSubjectService {

    Subject updateById(Integer id, SubjectInputDto subjectInputDto) throws NotFoundException;
}
