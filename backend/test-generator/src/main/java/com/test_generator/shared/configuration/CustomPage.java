package com.test_generator.shared.configuration;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomPage<T> {

    private List<T> content;
    private int totalPages;
    private long totalElements;
    private int currentPage;
}
