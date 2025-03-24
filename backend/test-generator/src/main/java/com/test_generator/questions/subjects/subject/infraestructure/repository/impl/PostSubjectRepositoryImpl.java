package com.test_generator.questions.subjects.subject.infraestructure.repository.impl;


import com.test_generator.questions.subjects.subject.application.mapper.SubjectMapper;
import com.test_generator.questions.subjects.subject.domain.Subject;
import com.test_generator.questions.subjects.subject.domain.repository.PostSubjectRepository;
import com.test_generator.questions.subjects.subject.infraestructure.repository.SubjectJpaRepository;
import com.test_generator.questions.subjects.subject.infraestructure.repository.entity.SubjectJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostSubjectRepositoryImpl implements PostSubjectRepository {

    private final SubjectJpaRepository subjectJpaRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public Subject createSubject(Subject subject) {

        SubjectJpa subjectJpa = subjectJpaRepository.save(
                subjectMapper.domainToJpa(subject)
        );

        return subjectMapper.jpaToDomain(subjectJpa);
    }
}
