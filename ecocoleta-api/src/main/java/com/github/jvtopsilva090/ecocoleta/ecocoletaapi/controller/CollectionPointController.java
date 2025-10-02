package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.controller;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto.*;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.service.CollectionPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collection-point")
public class CollectionPointController {

    private final CollectionPointService collectionPointService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<CollectionPointOutDto>> createCollectionPoint(@RequestBody CollectionPointCreateDto collectionPointCreateDto) {
        final CollectionPointOutDto collectionPointOutDto = this.collectionPointService.createCollectionPoint(collectionPointCreateDto);
        return ResponseEntity.ok(new ApiResponseDto<>(collectionPointOutDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<CollectionPointOutDto>>> getAllCollectionPoints(
            @RequestParam(name = "residue", required = false) String residueType,
            @RequestParam(name = "name", required = false) String name
    ) {
        final List<CollectionPointOutDto> list = collectionPointService.getAllCollectionPoints(residueType, name);
        return ResponseEntity.ok(new ApiResponseDto<>(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<CollectionPointOutDto>> getCollectionPointById(@PathVariable("id") Integer collectionPointId) {
        final CollectionPointOutDto collectionPointOutDto = this.collectionPointService.getCollectionPointById(collectionPointId);
        return ResponseEntity.ok(new ApiResponseDto<>(collectionPointOutDto));
    }

    @PutMapping
    public ResponseEntity<ApiResponseDto<CollectionPointOutDto>> updateCollectionPoint(@RequestBody CollectionPointEditDto collectionPointEditDtos) {
        final CollectionPointOutDto collectionPoints = this.collectionPointService.updateCollectionPoint(collectionPointEditDtos);
        return ResponseEntity.ok(new ApiResponseDto<>(true, "Collection Points updated successfully!", collectionPoints));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Object>> deleteCollectionPoint(@PathVariable("id") Integer collectionPointId) {
        this.collectionPointService.deleteCollectionPoint(collectionPointId);
        return ResponseEntity.ok(new ApiResponseDto<>(true, "Collection point deleted successfully!"));
    }
}