package com.test_generator.questions.subjects.subject.application.impl;

import com.test_generator.shared.configuration.CustomPage;
import com.test_generator.shared.exceptions.NotFoundException;
import com.test_generator.questions.subjects.subject.application.GetSubjectService;
import com.test_generator.questions.subjects.subject.domain.Subject;
import com.test_generator.questions.subjects.subject.domain.repository.GetSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetSubjectServiceImpl implements GetSubjectService {

    private final GetSubjectRepository getSubjectRepository;

    @Override
    public Subject findById(Integer id) throws NotFoundException {
        return getSubjectRepository.findById(id);
    }

    @Override
    public Optional<Subject> findByName(String name) throws NotFoundException {
        return getSubjectRepository.findByName(name);
    }

    @Override
    public CustomPage<Subject> findAll(PageRequest pageRequest) {

        return getSubjectRepository.findAll(pageRequest);
    }
}
