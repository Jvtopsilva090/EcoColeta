package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.repository;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.CollectionPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CollectionPointRepository extends JpaRepository<CollectionPoint, Integer> {

    @Query("""
        select cp
        from CollectionPoint cp
        where (:collection_point_name is null or lower(cp.name) ilike '%' || lower(:collection_point_name) || '%')
    """) Page<CollectionPoint> findAllPaginated(
        Pageable pageable,
        @Param("collection_point_name") String collectionPointName
    );
}
