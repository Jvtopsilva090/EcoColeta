package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto;

import lombok.NonNull;

public record CollectionPointEditDto(
    @NonNull Long id,
    String name,
    String formattedAddress,
    Double latitude,
    Double longitude
) {
}
