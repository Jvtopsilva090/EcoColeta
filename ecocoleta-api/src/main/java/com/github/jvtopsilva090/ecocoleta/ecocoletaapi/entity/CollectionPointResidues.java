package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "collection_point_residues")
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
