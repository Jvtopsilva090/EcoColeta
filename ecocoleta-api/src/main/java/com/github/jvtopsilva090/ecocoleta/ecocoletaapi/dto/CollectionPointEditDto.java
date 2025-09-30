package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto;

import lombok.NonNull;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public record CollectionPointEditDto(
    @Param("id") @NonNull Integer id,
    @Param("name") String name,
    @Param("formattedAddress") String formattedAddress,
    @Param("latitude") BigDecimal latitude,
    @Param("longitude") BigDecimal longitude
) {}
