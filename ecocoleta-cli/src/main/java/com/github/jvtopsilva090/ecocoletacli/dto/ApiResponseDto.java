package com.github.jvtopsilva090.ecocoletacli.dto;

public record ApiResponseDto<T>(
    Boolean success,
    String message,
    String error,
    T data,
    PageMetadataDto pagination
) {}
