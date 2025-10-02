package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.repository;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.CollectionPoint;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.projection.CollectionPointFlatProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CollectionPointRepository extends JpaRepository<CollectionPoint, Integer> {

    @Modifying
    @Query("""
        select
            cp.id as idCollectionPoint,
            cp.name as nameCollectionPoint,
            cp.formattedAddress as addressCollectionPoint,
            cp.latitude as latitude,
            cp.longitude as longitude,
            r.id as idResidue,
            r.name as nameResidue
        from CollectionPoint cp
        left join ResiduesCollectionPoint rcp on rcp.idCollectionPoint = cp.id
        left join Residue r on r.id = rcp.idResidue
        where 1=1
            and (:residue_type is null or lower(r.name) like lower(concat('%', :residue_type, '%')))
            and (:name is null or lower(cp.name) like lower(concat('%', :name, '%')))
    """) List<CollectionPointFlatProjection> findAll(
            @Param("residue_type") String residueType,
            @Param("name") String name
    );
}
