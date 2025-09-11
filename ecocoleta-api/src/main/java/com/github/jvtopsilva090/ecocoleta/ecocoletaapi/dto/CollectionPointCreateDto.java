package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto;

import java.util.List;

public record CollectionPointCreateDto(
    String name,
    String formattedAddress,
    Double latitude,
    Double longitude,
    List<Long> residueIds
) {}
