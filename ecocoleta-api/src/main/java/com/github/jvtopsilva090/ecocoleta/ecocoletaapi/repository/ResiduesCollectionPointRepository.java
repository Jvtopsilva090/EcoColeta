package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.repository;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.Residue;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.ResiduesCollectionPoint;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResiduesCollectionPointRepository extends CrudRepository<ResiduesCollectionPoint, Integer> {
    void deleteByIdCollectionPoint(Integer idCollectionPoint);

    @Query("""
        select r
        from ResiduesCollectionPoint rcp
        left join Residue r on rcp.idResidue = r.id
        where rcp.idCollectionPoint = :id_collection_point
    """) List<Residue> getResidueByIdCollectionPoint(
        @Param("id_collection_point") Integer idCollectionPoint
    );
}
