package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto;

import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public record CollectionPointCreateDto(
    @Param("name") String name,
    @Param("formattedAddress") String formattedAddress,
    @Param("latitude") BigDecimal latitude,
    @Param("longitude") BigDecimal longitude,
    @Param("residueIds") List<Integer> residueIds
) {}
