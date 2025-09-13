package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.repository;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.Residue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResidueRepository extends JpaRepository<Residue, Integer> {

    @Query("""
        select r from Residue r where (:residue_name is null or lower(r.name) ilike '%' || :residue_name || '%')
    """) List<Residue> findAllCustom(@Param("residue_name") String residueName);

    @Query("""
        select case when count(r) > 0 then true else false end
        from Residue r where lower(r.name) in :residues_names
    """) boolean existsDuplicated(@Param("residues_names") List<String> residuesList);
}
