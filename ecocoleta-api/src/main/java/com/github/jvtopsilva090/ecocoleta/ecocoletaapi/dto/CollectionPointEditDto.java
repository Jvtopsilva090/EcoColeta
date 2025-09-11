package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto;

import lombok.NonNull;

import java.util.List;

public record CollectionPointEditDto(
    @NonNull Long id,
    String name,
    String formattedAddress,
    Double latitude,
    Double longitude,
    List<Long> residueIds
) {
}
