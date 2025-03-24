package com.test_generator.questions.question.application.impl;

import com.test_generator.questions.question.application.GetQuestionService;
import com.test_generator.questions.question.domain.Question;
import com.test_generator.questions.question.domain.repository.GetQuestionRepository;
import com.test_generator.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import com.test_generator.shared.configuration.CustomPage;
import org.hibernate.query.SortDirection;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.test_generator.shared.constants.Constants.RECENT_MINUTES;

@Service
@RequiredArgsConstructor
public class GetQuestionServiceImpl implements GetQuestionService {

    private final GetQuestionRepository getQuestionRepository;

    @Override
    public Question findById(Integer id) throws NotFoundException {
        return getQuestionRepository.findById(id);
    }

    @Override
    public CustomPage<Question> findAll(PageRequest pageRequest) {

        return getQuestionRepository.findAll(pageRequest);
    }

    @Override
    public CustomPage<Question> findByDateAfter(PageRequest pageRequest, LocalDateTime generationDate) {

        LocalDateTime dateFilter = Objects.isNull(generationDate)
                ? LocalDateTime.now().minusMinutes(RECENT_MINUTES)
                : generationDate;

        return getQuestionRepository.findByDateAfter(pageRequest, dateFilter);
    }
}
