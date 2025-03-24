package com.test_generator.questions.subjects.subject.application.impl;

import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.subject.application.GetSubjectService;
import com.test_generator.questions.subjects.subject.application.PutSubjectService;
import com.test_generator.questions.subjects.subject.application.mapper.SubjectMapper;
import com.test_generator.questions.subjects.subject.domain.Subject;
import com.test_generator.questions.subjects.subject.domain.repository.PutSubjectRepository;
import com.test_generator.questions.subjects.subject.infraestructure.dtos.input.SubjectInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PutSubjectServiceImpl implements PutSubjectService {

    private final SubjectMapper subjectMapper;
    private final PutSubjectRepository putSubjectRepository;
    private final GetSubjectService getSubjectService;

    @Override
    public Subject updateById(Integer id, SubjectInputDto subjectInputDto) throws NotFoundException {

        Subject subject = getSubjectService.findById(id);

        Subject updatedSubject = subjectMapper.update(subject, subjectInputDto);

        return putSubjectRepository.updateSubject(updatedSubject);
    }
}
