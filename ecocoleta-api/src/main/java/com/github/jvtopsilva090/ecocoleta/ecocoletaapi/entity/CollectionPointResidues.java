package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "collection_point_residues", uniqueConstraints = @UniqueConstraint(columnNames = {"collectionPointId", "residueId"}))
public class CollectionPointResidues {

    @EmbeddedId
    private CollectionPointResiduesId id;

    @Column(name = "collection_point_id", nullable = false, insertable = false, updatable = false)
    private Long collectionPointId;

    @Column(name = "residue_id", nullable = false, insertable = false, updatable = false)
    private Long residueId;

    public CollectionPointResidues(Long collectionPointId, Long residueId) {
        this.id = new CollectionPointResiduesId(collectionPointId, residueId);
        this.collectionPointId = collectionPointId;
        this.residueId = residueId;
    }
}
