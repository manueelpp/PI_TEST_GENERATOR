package com.test_generator.shared.services.impl;

import com.test_generator.shared.configuration.CustomPage;
import com.test_generator.shared.services.CreateOutputService;
import com.test_generator.shared.services.GenericMapper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreateOutputServiceImpl implements CreateOutputService {

    @Override
    public <T, R> R entityToOutput(T entity, GenericMapper<T, R> mapper) {

        return mapper.map(entity);
    }

    @Override
    public <T, R> R entityToOutput(Optional<T> entity, GenericMapper<T, R> mapper) {

        return entity.map(mapper::map)
                .orElse(null);
    }

    @Override
    public <T, R> CustomPage<R> pageToOutput(CustomPage<T> customPage, GenericMapper<T, R> mapper) {

        List<R> content = customPage.getContent()
                .stream()
                .map(mapper::map)
                .toList();

        return CustomPage.<R>builder()
                .content(content)
                .totalElements(customPage.getTotalElements())
                .totalPages(customPage.getTotalPages())
                .currentPage(customPage.getCurrentPage())
                .build();
    }

    @Override
    public PageRequest createSortedPageRequest(int page, int size, String sortBy, SortDirection sortDirection) {

        String sortField = StringUtils.isBlank(sortBy) ? "id" : sortBy;

        return PageRequest.of(
                page,
                size,
                Sort.by(
                        sortDirection == SortDirection.ASCENDING
                                ? Sort.Order.asc(sortField)
                                : Sort.Order.desc(sortField)
                )
        );

    }
}
