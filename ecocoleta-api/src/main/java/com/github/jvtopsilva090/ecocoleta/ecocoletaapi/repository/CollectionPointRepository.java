package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.repository;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.CollectionPoint;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.projection.CollectionPointFlatProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CollectionPointRepository extends JpaRepository<CollectionPoint, Integer> {

    @Query(
        value = """
            select
                cp.id as collectionPointId,
                cp.name as collectionPointName,
                cp.formattedAddress as collectionPointAddress,
                cp.latitude as collectionPointLatitude,
                cp.longitude as collectionPointLongitude,
                r.id as residueId,
                r.name as residueName
            from CollectionPoint cp
                left join CollectionPointResidues cpr on cpr.collectionPointId = cp.id
                inner join Residue r on r.id = cpr.residueId
            where (:collection_point_name is null or lower(cp.name) ilike '%' || lower(:collection_point_name) || '%')
        """,
        countQuery = "select count(cp) from CollectionPoint cp"
    ) Page<CollectionPointFlatProjection> findAllPaginated(
        Pageable pageable,
        @Param("collection_point_name") String collectionPointName
    );

    @Query("""
        select
            cp.id as collectionPointId,
            cp.name as collectionPointName,
            cp.formattedAddress as collectionPointAddress,
            cp.latitude as collectionPointLatitude,
            cp.longitude as collectionPointLongitude,
            r.id as residueId,
            r.name as residueName
        from CollectionPoint cp
            left join CollectionPointResidues cpr on cpr.collectionPointId = cp.id
            inner join Residue r on r.id = cpr.residueId
        where cp.id = :id
    """) Optional<List<CollectionPointFlatProjection>> findByIdCustom(@Param("id") Integer id);
}
