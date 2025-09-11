package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.repository;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.CollectionPointResidues;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.CollectionPointResiduesId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionPointResiduesRepository extends JpaRepository<CollectionPointResidues, CollectionPointResiduesId> {
    void deleteAllByCollectionPointId(Integer collectionPointId);
}
