package com.github.jvtopsilva090.ecocoletacli.dto;

public record PageMetadataDto(
    int page,
    int size,
    long totalItems,
    int totalPages
) {}
