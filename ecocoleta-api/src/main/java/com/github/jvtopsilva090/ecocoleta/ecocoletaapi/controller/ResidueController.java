package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.controller;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.Residue;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.service.ResidueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/residue")
public class ResidueController {

    private final ResidueService residueService;

    @GetMapping
    public ResponseEntity<List<Residue>> getAllResidueTypes() {
        return ResponseEntity.ok(residueService.getAllResidueTypes());
    }
}
