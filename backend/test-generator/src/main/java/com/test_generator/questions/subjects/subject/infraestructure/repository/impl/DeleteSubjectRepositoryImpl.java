package com.test_generator.questions.subjects.subject.infraestructure.repository.impl;

import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.subject.domain.repository.DeleteSubjectRepository;
import com.test_generator.questions.subjects.subject.infraestructure.repository.SubjectJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.test_generator.shared.constants.Constants.SUBJECT_ENTITY;

@Repository
@RequiredArgsConstructor
public class DeleteSubjectRepositoryImpl implements DeleteSubjectRepository {

    private final SubjectJpaRepository subjectJpaRepository;

    @Override
    public void deleteById(Integer id) throws NotFoundException {

        subjectJpaRepository.deleteById(subjectJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(SUBJECT_ENTITY, id))
                .getId()
        );
    }
}
