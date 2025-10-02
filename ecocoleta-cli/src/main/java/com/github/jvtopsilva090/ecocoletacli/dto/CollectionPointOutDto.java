package com.github.jvtopsilva090.ecocoletacli.dto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public record CollectionPointOutDto (
        Integer id,
        String name,
        String formattedAddress,
        BigDecimal latitude,
        BigDecimal longitude,
        List<ResidueOutDto> residuesType
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
            TIPOS_DE_RESIDUOS: %s
            """
            , name, id, formattedAddress, latitude, longitude
            , ("\n").concat(Arrays.toString(residuesType.stream().map(residue -> ("\t").concat(residue.toString()).concat("\n")).toArray(String[]::new)))
        );
    }
}
