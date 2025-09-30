package com.github.jvtopsilva090.ecocoletacli.dto;

import java.math.BigDecimal;

public record CollectionPointOutDto (
    Integer id,
    String name,
    String formattedAddress,
    BigDecimal latitude,
    BigDecimal longitude
) {
    @Override
    public String toString() {
        return String.format(
            """
            COLLECTION_POINT: %s
            COLLECTION_POINT ID: %d
            FORMATTED_ADDRESS: %s
            LATITUDE: %f
            LONGITUDE: %f
            """
            , name, id, formattedAddress, latitude, longitude
        );
    }
}
