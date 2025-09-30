package com.github.jvtopsilva090.ecocoletacli.dto;

import java.math.BigDecimal;

public record CollectionPointCreateDto(
    String name,
    String formattedAddress,
    BigDecimal latitude,
    BigDecimal longitude
) {}
