package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.service;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto.ResidueEditDto;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto.ResidueOutDto;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.Residue;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.exception.ResidueException;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.exception.ResidueNotFoundException;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.repository.ResidueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResidueService {

    private final ResidueRepository residueRepository;

    public List<ResidueOutDto> createResidues(List<String> residuesList) {
        try {
            boolean exists = this.residueRepository.existsDuplicated(residuesList.stream().map(String::toLowerCase).toList());
            if (exists) throw new ResidueException("A Residue Type that already exists was found!");
            final List<Residue> createdResidues;
            createdResidues = this.residueRepository.saveAll(residuesList.stream().map(Residue::new).toList());
            return createdResidues.stream().map(ResidueOutDto::new).toList();
        } catch (ResidueException e) {
            throw e;
        } catch (Exception e) {
            throw new ResidueException("Failed to create residues type!");
        }
    }

    public List<ResidueOutDto> getAllResidues(String residueName) {
        try {
            final List<Residue> residues;
            residues = this.residueRepository.findAllCustom(residueName);
            return residues.stream().map(ResidueOutDto::new).toList();
        } catch (Exception e) {
            throw new ResidueException("Failed to get residues type!");
        }
    }

    public List<ResidueOutDto> updateResidues(List<ResidueEditDto> residuesUpdatingList) {
        try {
            final List<Residue> residues = new ArrayList<>();

            residuesUpdatingList.forEach(residueEditDto -> {
                final Residue residue;

                residue = this.residueRepository
                        .findById(residueEditDto.id())
                        .orElseThrow(() -> new ResidueNotFoundException("Failed to get Residue Type by id " + residueEditDto.id()));

                Optional.ofNullable(residueEditDto.name()).ifPresent(residue::setName);
                residues.add(this.residueRepository.save(residue));
            });

            return residues.stream().map(ResidueOutDto::new).toList();
        } catch (ResidueNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ResidueException("Failed to update residues type!");
        }
    }

    @Transactional
    public String deleteResidue(Integer residueId) {
        boolean exists = this.residueRepository.existsById(residueId);
        if (!exists) throw new ResidueNotFoundException("Failed to get Residue Type by id " + residueId);
        this.residueRepository.deleteById(residueId);
        return "Residue Type deleted successfully!";
    }
}
