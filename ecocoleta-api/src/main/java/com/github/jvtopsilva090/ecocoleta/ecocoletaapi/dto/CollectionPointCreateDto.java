package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto;

import java.math.BigDecimal;
import java.util.List;

public record CollectionPointCreateDto(
    String name,
    String formattedAddress,
    BigDecimal latitude,
    BigDecimal longitude,
    List<Integer> residueIds
) {}
