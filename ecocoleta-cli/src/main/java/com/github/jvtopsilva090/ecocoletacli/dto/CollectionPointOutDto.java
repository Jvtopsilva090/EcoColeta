package com.github.jvtopsilva090.ecocoletacli.dto;

import java.math.BigDecimal;
import java.util.List;

public record CollectionPointOutDto (
    Integer id,
    String name,
    String formattedAddress,
    BigDecimal latitude,
    BigDecimal longitude,
    List<ResidueOutDto> residuesType
) {}
