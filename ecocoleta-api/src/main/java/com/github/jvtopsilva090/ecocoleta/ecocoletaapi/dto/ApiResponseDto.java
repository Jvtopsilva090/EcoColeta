package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto;

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

    public ApiResponseDto(T data) {
        this(null, null, null, data, null);
    }

    public <R> ApiResponseDto(Page<R> page, T data) {
        this(
            null,
            null,
            null,
            data,
            new PageMetadataDto(page)
        );
    }
}
