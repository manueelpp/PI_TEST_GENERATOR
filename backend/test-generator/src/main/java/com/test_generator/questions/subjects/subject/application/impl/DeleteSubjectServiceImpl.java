package com.test_generator.questions.subjects.subject.application.impl;

import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.subject.application.DeleteSubjectService;
import com.test_generator.questions.subjects.subject.domain.repository.DeleteSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteSubjectServiceImpl implements DeleteSubjectService {

    private final DeleteSubjectRepository deleteSubjectRepository;

    @Override
    public void deleteById(Integer id) throws NotFoundException {

        deleteSubjectRepository.deleteById(id);
    }
}
