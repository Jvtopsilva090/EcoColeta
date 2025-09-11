package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CollectionPointResiduesId {

    @Column(name = "collection_point_id", nullable = false)
    private Integer collectionPointId;

    @Column(name = "residue_id", nullable = false)
    private Integer residueId;
}
