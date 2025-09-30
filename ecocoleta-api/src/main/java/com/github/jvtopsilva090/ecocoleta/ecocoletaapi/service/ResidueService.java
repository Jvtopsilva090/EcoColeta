package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.service;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.Residue;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.repository.ResidueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResidueService {

    private ResidueRepository residueRepository;

    public List<Residue> getAllResidueTypes() {
        return residueRepository.findAll();
    }
}
