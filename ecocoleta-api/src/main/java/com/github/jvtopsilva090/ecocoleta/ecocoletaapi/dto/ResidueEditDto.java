package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto;

import org.springframework.data.repository.query.Param;

public record ResidueEditDto(
    @Param("id") Integer id,
    @Param("name") String name
) {}
