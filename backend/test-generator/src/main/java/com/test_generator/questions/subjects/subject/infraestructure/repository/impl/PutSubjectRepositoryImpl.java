package com.test_generator.questions.subjects.subject.infraestructure.repository.impl;

import com.test_generator.questions.subjects.subject.application.mapper.SubjectMapper;
import com.test_generator.questions.subjects.subject.domain.Subject;
import com.test_generator.questions.subjects.subject.domain.repository.PutSubjectRepository;
import com.test_generator.questions.subjects.subject.infraestructure.repository.SubjectJpaRepository;
import com.test_generator.questions.subjects.subject.infraestructure.repository.entity.SubjectJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PutSubjectRepositoryImpl implements PutSubjectRepository {

    private final SubjectMapper subjectMapper;
    private final SubjectJpaRepository subjectJpaRepository;
    @Override
    public Subject updateSubject(Subject subject) {

        SubjectJpa subjectJpa= subjectJpaRepository.save(
                subjectMapper.domainToJpa(subject)
        );

        return subjectMapper.jpaToDomain(subjectJpa);
    }
}
