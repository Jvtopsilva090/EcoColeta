package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.controller;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto.ApiResponseDto;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto.ResidueEditDto;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto.ResidueOutDto;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.service.ResidueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/residue")
public class ResidueController {

    private final ResidueService residueService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<List<ResidueOutDto>>> createResidues(@RequestBody List<String> residuesList) {
        List<ResidueOutDto> residues = this.residueService.createResidues(residuesList);
        return ResponseEntity.ok(new ApiResponseDto<>(residues));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ResidueOutDto>>> getAllResidues(@RequestParam(name = "residueName", required = false) String residueName) {
        List<ResidueOutDto> residues = this.residueService.getAllResidues(residueName);
        return ResponseEntity.ok(new ApiResponseDto<>(residues));
    }

    @PutMapping
    public ResponseEntity<ApiResponseDto<List<ResidueOutDto>>> updateResidues(@RequestBody List<ResidueEditDto> residuesUpdatingList) {
        List<ResidueOutDto> residues = this.residueService.updateResidues(residuesUpdatingList);
        return ResponseEntity.ok(new ApiResponseDto<>(residues));
    }

    @DeleteMapping("/{residueId}")
    public ResponseEntity<ApiResponseDto<Object>> deleteResidue(@PathVariable("residueId") Integer residueId) {
        String successMessage = this.residueService.deleteResidue(residueId);
        return ResponseEntity.ok(new ApiResponseDto<>(true, successMessage));
    }
}
