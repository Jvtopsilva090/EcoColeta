package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "collection_point_residues", uniqueConstraints = @UniqueConstraint(columnNames = {"collection_point_id", "residue_id"}))
public class CollectionPointResidues {

    @EmbeddedId
    private CollectionPointResiduesId id;

    @Column(name = "collection_point_id", nullable = false, insertable = false, updatable = false)
    private Integer collectionPointId;

    @Column(name = "residue_id", nullable = false, insertable = false, updatable = false)
    private Integer residueId;

    public CollectionPointResidues(Integer collectionPointId, Integer residueId) {
        this.id = new CollectionPointResiduesId(collectionPointId, residueId);
        this.collectionPointId = collectionPointId;
        this.residueId = residueId;
    }
}
