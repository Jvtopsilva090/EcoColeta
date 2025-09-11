package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto;

import org.springframework.cglib.core.internal.Function;
import org.springframework.data.domain.Page;

public record ApiResponseDto<T>(
    Boolean success,
    String message,
    String error,
    T data,
    PageMetadataDto pagination
) {
    public ApiResponseDto(boolean success, String message) {
        this(success, message, null, null, null);
    }

    public ApiResponseDto(boolean success, String message, String error) {
        this(success, message, error, null, null);
    }

    public ApiResponseDto(boolean success, String message, T data) {
        this(success, message, null, data, null);
    }

    public ApiResponseDto(T data, PageMetadataDto pagination) {
        this(null, null, null, data, pagination);
    }

    public ApiResponseDto(T data) {
        this(null, null, null, data, null);
    }

    public <R> ApiResponseDto(Page<R> page, Function<Page<R>, T> mapper) {
        this(
            null,
            null,
            null,
            mapper.apply(page),
            new PageMetadataDto(page)
        );
    }
}
