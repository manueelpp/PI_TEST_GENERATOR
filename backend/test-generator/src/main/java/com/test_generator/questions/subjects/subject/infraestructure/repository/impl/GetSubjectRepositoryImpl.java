package com.test_generator.questions.subjects.subject.infraestructure.repository.impl;


import com.test_generator.shared.configuration.CustomPage;
import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.subject.application.mapper.SubjectMapper;
import com.test_generator.questions.subjects.subject.domain.Subject;
import com.test_generator.questions.subjects.subject.domain.repository.GetSubjectRepository;
import com.test_generator.questions.subjects.subject.infraestructure.repository.SubjectJpaRepository;
import com.test_generator.questions.subjects.subject.infraestructure.repository.entity.SubjectJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.test_generator.shared.constants.Constants.SUBJECT_ENTITY;

@Repository
@RequiredArgsConstructor
public class GetSubjectRepositoryImpl implements GetSubjectRepository {

    private final SubjectMapper subjectMapper;
    private final SubjectJpaRepository subjectJpaRepository;

    @Override
    public Subject findById(Integer id) throws NotFoundException {

        SubjectJpa subjectJpa = subjectJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(SUBJECT_ENTITY, id));

        return subjectMapper.jpaToDomain(subjectJpa);
    }

    @Override
    public Optional<Subject> findByName(String name) throws NotFoundException {

        Optional<SubjectJpa> subjectJpa = subjectJpaRepository.findByNameIgnoreCase(name);

        return subjectJpa.map(subjectMapper::jpaToDomain);
    }

    @Override
    public CustomPage<Subject> findAll(PageRequest pageRequest) {

        Page<SubjectJpa> subjectJpaCustomPage = subjectJpaRepository.findAll(pageRequest);

        List<Subject> subjectList = subjectJpaCustomPage.getContent()
                .stream()
                .map(subjectMapper::jpaToDomain)
                .toList();

        return CustomPage.<Subject>builder()
                .content(subjectList)
                .totalPages(subjectJpaCustomPage.getTotalPages())
                .totalElements(subjectJpaCustomPage.getTotalElements())
                .currentPage(subjectJpaCustomPage.getNumber())
                .build();
    }
}
