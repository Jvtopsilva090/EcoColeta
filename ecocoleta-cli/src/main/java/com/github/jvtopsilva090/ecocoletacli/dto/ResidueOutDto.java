package com.github.jvtopsilva090.ecocoletacli.dto;

public record ResidueOutDto(
    Integer id,
    String name
) {
    @Override
    public String toString() {
        return String.format("id: %d, TipoColeta: %s", id, name);
    }
}
