package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.repository;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.ResiduesCollectionPoint;
import org.springframework.data.repository.CrudRepository;

public interface ResiduesCollectionPointRepository extends CrudRepository<ResiduesCollectionPoint, Integer> {
    void deleteByIdCollectionPoint(Integer idCollectionPoint);
}
