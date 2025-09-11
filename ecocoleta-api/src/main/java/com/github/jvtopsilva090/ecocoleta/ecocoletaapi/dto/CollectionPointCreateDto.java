package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto;

public record CollectionPointCreateDto(
    String name,
    String formattedAddress,
    Double latitude,
    Double longitude
) {}
