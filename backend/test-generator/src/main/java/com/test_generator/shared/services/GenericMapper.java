package com.test_generator.shared.services;

@FunctionalInterface
public interface GenericMapper<T, R> {
    R map(T source);
}
