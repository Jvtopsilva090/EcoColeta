package com.github.jvtopsilva090.ecocoleta.dto;

public record ApiResponseDto<T>(
    Boolean success,
    String message,
    String error,
    T data,
    PageMetadataDto pagination
) {}
