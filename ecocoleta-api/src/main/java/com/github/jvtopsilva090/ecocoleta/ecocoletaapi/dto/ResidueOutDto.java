package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.Residue;

public record ResidueOutDto(
    Integer id,
    String name
) {
    public ResidueOutDto(Residue residue) {
        this(residue.getId(), residue.getName());
    }
}
