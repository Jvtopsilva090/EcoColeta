package com.github.jvtopsilva090.ecocoletacli.dto;

import java.math.BigDecimal;

public record CollectionPointFiltersDto(
    String name,
    BigDecimal latitude,
    BigDecimal longitude
) {}
