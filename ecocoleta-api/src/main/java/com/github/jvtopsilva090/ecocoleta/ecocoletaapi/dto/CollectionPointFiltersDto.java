package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto;

import org.springframework.web.bind.annotation.RequestParam;

public record CollectionPointFiltersDto(
    @RequestParam(name = "pointName", required = false) String collectionPointName
) {}
