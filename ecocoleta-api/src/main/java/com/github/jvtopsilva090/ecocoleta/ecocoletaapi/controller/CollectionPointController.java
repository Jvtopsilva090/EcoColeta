package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.controller;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto.*;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.service.CollectionPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
        return ResponseEntity.ok(collectionPointService.createCollectionPoint(collectionPointCreateDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<CollectionPointOutDto>>> getAllCollectionPoints(
        CollectionPointFiltersDto filtersDto,
        @PageableDefault() Pageable pageable
    ) {
        return ResponseEntity.ok(collectionPointService.getAllCollectionPoints(filtersDto, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<CollectionPointOutDto>> getCollectionPointById(@PathVariable("id") Integer collectionPointId) {
        return ResponseEntity.ok(collectionPointService.getCollectionPointById(collectionPointId));
    }

    @PutMapping
    public ResponseEntity<ApiResponseDto<List<CollectionPointOutDto>>> updateCollectionPoint(@RequestBody List<CollectionPointEditDto> collectionPointEditDtos) {
        return ResponseEntity.ok(collectionPointService.updateCollectionPoint(collectionPointEditDtos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Object>> deleteCollectionPoint(@PathVariable("id") Integer collectionPointId) {
        return ResponseEntity.ok(collectionPointService.deleteCollectionPoint(collectionPointId));
    }
}