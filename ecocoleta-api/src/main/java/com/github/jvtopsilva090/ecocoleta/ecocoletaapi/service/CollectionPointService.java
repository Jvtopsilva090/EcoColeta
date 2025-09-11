package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.service;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto.*;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.CollectionPoint;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.CollectionPointResidues;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.exception.CollectionPointNotFoundException;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.repository.CollectionPointRepository;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.repository.CollectionPointResiduesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectionPointService {

    private final CollectionPointRepository collectionPointRepository;
    private final CollectionPointResiduesRepository collectionPointResiduesRepository;

    public ApiResponseDto<CollectionPointOutDto> createCollectionPoint(final CollectionPointCreateDto collectionPointCreateDto) {
        try {
            final CollectionPoint collectionPoint;
            final CollectionPointOutDto collectionPointOutDto;
            final List<CollectionPointResidues> collectionPointResidues;

            collectionPoint = this.collectionPointRepository.save(new CollectionPoint(collectionPointCreateDto));
            collectionPointOutDto = new CollectionPointOutDto(collectionPoint);

            collectionPointResidues = collectionPointCreateDto
                    .residueIds()
                    .stream()
                    .map(residueId -> new CollectionPointResidues(collectionPointOutDto.id(), residueId))
                    .toList();

            this.collectionPointResiduesRepository.saveAll(collectionPointResidues);

            return new ApiResponseDto<>(collectionPointOutDto);
        } catch (Exception e) {
            return new ApiResponseDto<>(false, "Failed to create collection point!", e.getMessage());
        }
    }

    public ApiResponseDto<List<CollectionPointOutDto>> getAllCollectionPoints(CollectionPointFiltersDto filtersDto, Pageable pageable) {
        try {
            final Page<CollectionPoint> paginatedCollectionPoints;
            paginatedCollectionPoints = this.collectionPointRepository.findAllPaginated(pageable, filtersDto.collectionPointName());
            return new ApiResponseDto<>(paginatedCollectionPoints, (page) -> page.getContent().stream().map(CollectionPointOutDto::new).toList());
        } catch (Exception e) {
            return new ApiResponseDto<>(false, "Failed to get collection points!", e.getMessage());
        }
    }

    public ApiResponseDto<CollectionPointOutDto> getCollectionPointById(final Integer collectionPointId) {
        try {
            final CollectionPoint collectionPoint;
            final CollectionPointOutDto collectionPointOutDto;

            collectionPoint = collectionPointRepository
                    .findById(collectionPointId)
                    .orElseThrow(() -> new CollectionPointNotFoundException("Collection Point not found for id " + collectionPointId));

            collectionPointOutDto = new CollectionPointOutDto(collectionPoint);

            return new ApiResponseDto<>(collectionPointOutDto);
        } catch (Exception e) {
            return new ApiResponseDto<>(false, "Failed to get collection point by id!", e.getMessage());
        }
    }

    @Transactional
    public ApiResponseDto<List<CollectionPointOutDto>> updateCollectionPoint(final List<CollectionPointEditDto> collectionPointEditDtos) {
        try {
            final List<CollectionPoint> collectionPoints = new ArrayList<>();

            collectionPointEditDtos.forEach(collectionPointEditDto -> {
                final CollectionPoint collectionPoint;
                final List<CollectionPointResidues> collectionPointResidues;

                collectionPoint = collectionPointRepository
                        .findById(collectionPointEditDto.id())
                        .orElseThrow(() -> new CollectionPointNotFoundException("Collection Point not found for id " + collectionPointEditDto.id()));

                Optional.ofNullable(collectionPointEditDto.name()).ifPresent(collectionPoint::setName);
                Optional.ofNullable(collectionPointEditDto.formattedAddress()).ifPresent(collectionPoint::setFormattedAddress);
                Optional.ofNullable(collectionPointEditDto.latitude()).ifPresent(collectionPoint::setLatitude);
                Optional.ofNullable(collectionPointEditDto.longitude()).ifPresent(collectionPoint::setLongitude);

                collectionPoints.add(collectionPoint);

                this.collectionPointResiduesRepository.deleteAllByCollectionPointId(collectionPoint.getId());

                collectionPointResidues = collectionPointEditDto
                        .residueIds()
                        .stream()
                        .map(residueId -> new CollectionPointResidues(collectionPointEditDto.id(), residueId))
                        .toList();

                this.collectionPointResiduesRepository.saveAll(collectionPointResidues);
            });

            this.collectionPointRepository.saveAll(collectionPoints);

            return new ApiResponseDto<>(true, "Collection Points updated successfully!", collectionPoints.stream().map(CollectionPointOutDto::new).toList());
        } catch (Exception e) {
            return new ApiResponseDto<>(false, "Failed to delete collection point!", e.getMessage());
        }
    }

    @Transactional
    public ApiResponseDto<Object> deleteCollectionPoint(final Integer collectionPointId) {
        try {
            this.collectionPointResiduesRepository.deleteAllByCollectionPointId(collectionPointId);
            this.collectionPointRepository.deleteById(collectionPointId);
            return new ApiResponseDto<>(true, "Collection point deleted successfully!");
        } catch (Exception e) {
            return new ApiResponseDto<>(false, "Failed to delete collection point!", e.getMessage());
        }
    }
}