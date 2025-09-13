package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto;

import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

public record CollectionPointFiltersDto(
    @RequestParam(name = "name", required = false) String name,
    @RequestParam(name = "latitude", required = false) BigDecimal latitude,
    @RequestParam(name = "longitude", required = false) BigDecimal longitude
) {}
