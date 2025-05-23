package com.example.modulodocentes.strategy;

public interface ValidationStrategy<T> {
    void validate(T entity);
}