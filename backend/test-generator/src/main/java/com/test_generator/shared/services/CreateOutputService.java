package com.test_generator.shared.services;

import com.test_generator.shared.configuration.CustomPage;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface CreateOutputService {

    <T, R> R entityToOutput(T entity, GenericMapper<T, R> mapper);

    <T, R> R entityToOutput(Optional<T> entity, GenericMapper<T, R> mapper);

    <T, R> CustomPage<R> pageToOutput(CustomPage<T> customPage, GenericMapper<T, R> mapper);

    PageRequest createSortedPageRequest(int page, int size, String sortBy, SortDirection sortDirection);
}
