package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto;

import org.springframework.data.domain.Page;

public record PageMetadataDto(
    int page,
    int size,
    long totalItems,
    int totalPages
) {
    public <T> PageMetadataDto(Page<T> page) {
        this(page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
