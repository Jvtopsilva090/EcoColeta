package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.CollectionPoint;

import java.util.List;

public record CollectionPointOutDto(
    Long id,
    String name,
    String formattedAddress,
    Double latitude,
    Double longitude,
    List<Object> residuesType
) {
    public CollectionPointOutDto(CollectionPoint collectionPoint) {
        this(
            collectionPoint.getId(),
            collectionPoint.getName(),
            collectionPoint.getFormattedAddress(),
            collectionPoint.getLatitude(),
            collectionPoint.getLongitude(),
            null
        );
    }
}
