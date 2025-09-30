package com.github.jvtopsilva090.ecocoletacli.dto;

import lombok.NonNull;

import java.math.BigDecimal;

public record CollectionPointEditDto(
    @NonNull Integer id,
    String name,
    String formattedAddress,
    BigDecimal latitude,
    BigDecimal longitude
) {}
