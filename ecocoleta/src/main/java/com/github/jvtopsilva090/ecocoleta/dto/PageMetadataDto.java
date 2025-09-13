package com.github.jvtopsilva090.ecocoleta.dto;

public record PageMetadataDto(
    int page,
    int size,
    long totalItems,
    int totalPages
) {}
