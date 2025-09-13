package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.service;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto.*;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.CollectionPoint;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.CollectionPointResidues;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.exception.CollectionPointException;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.exception.CollectionPointNotFoundException;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.projection.CollectionPointFlatProjection;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.repository.CollectionPointRepository;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.repository.CollectionPointResiduesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CollectionPointService {

    private final CollectionPointRepository collectionPointRepository;
    private final CollectionPointResiduesRepository collectionPointResiduesRepository;

    public CollectionPointOutDto createCollectionPoint(final CollectionPointCreateDto collectionPointCreateDto) {
        try {
            final CollectionPoint collectionPoint;
            final CollectionPointOutDto collectionPointOutDto;
            final List<CollectionPointResidues> collectionPointResidues;

            collectionPoint = this.collectionPointRepository.save(new CollectionPoint(collectionPointCreateDto));
            collectionPointOutDto = new CollectionPointOutDto(collectionPoint);

            collectionPointResidues = collectionPointCreateDto
                    .residueIds()
                    .stream()
                    .map(residueId -> new CollectionPointResidues(collectionPointOutDto.getId(), residueId))
                    .toList();

            this.collectionPointResiduesRepository.saveAll(collectionPointResidues);

            return collectionPointOutDto;
        } catch (Exception e) {
            throw new CollectionPointException("Failed to create collection point!");
        }
    }

    public Page<CollectionPointOutDto> getAllCollectionPoints(CollectionPointFiltersDto filtersDto, Pageable pageable) {
        try {
            final Page<CollectionPointFlatProjection> paginatedCollectionPoints;
            final Map<Integer, CollectionPointOutDto> grouped;
            paginatedCollectionPoints = this.collectionPointRepository.findAllPaginated(pageable, filtersDto.name(), filtersDto.latitude(), filtersDto.longitude());
            grouped = getGroupedCollectionPoint(paginatedCollectionPoints.getContent());
            return new PageImpl<>(new ArrayList<>(grouped.values()), paginatedCollectionPoints.getPageable(), paginatedCollectionPoints.getTotalElements());
        } catch (Exception e) {
            throw new CollectionPointException("Failed to get collection points!");
        }
    }

    public CollectionPointOutDto getCollectionPointById(final Integer collectionPointId) {
        try {
            final List<CollectionPointFlatProjection> collectionPoint;
            final Map<Integer, CollectionPointOutDto> grouped;

            collectionPoint = collectionPointRepository
                    .findByIdCustom(collectionPointId)
                    .orElseThrow(() -> new CollectionPointNotFoundException("Collection Point not found for id " + collectionPointId));

            grouped = getGroupedCollectionPoint(collectionPoint);
            return grouped.get(collectionPointId);
        } catch (CollectionPointNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CollectionPointException("Failed to get collection point by id!");
        }
    }

    @Transactional
    public List<CollectionPointOutDto> updateCollectionPoint(final List<CollectionPointEditDto> collectionPointEditDtos) {
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

            return collectionPoints.stream().map(CollectionPointOutDto::new).toList();
        } catch (CollectionPointNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CollectionPointException("Failed to delete collection point!");
        }
    }

    @Transactional
    public void deleteCollectionPoint(final Integer collectionPointId) {
        try {
            this.collectionPointResiduesRepository.deleteAllByCollectionPointId(collectionPointId);
            this.collectionPointRepository.deleteById(collectionPointId);
        } catch (Exception e) {
            throw new CollectionPointException("Failed to delete collection point!");
        }
    }

    private Map<Integer, CollectionPointOutDto> getGroupedCollectionPoint(List<CollectionPointFlatProjection> data) {
        final Map<Integer, CollectionPointOutDto> grouped = new LinkedHashMap<>();

        for (CollectionPointFlatProjection row : data) {
            grouped.computeIfAbsent(
                    row.getCollectionPointId(),
                    id -> new CollectionPointOutDto(id, row)
            ).getResiduesType().add(new ResidueOutDto(row.getResidueId(), row.getResidueName()));
        }

        return grouped;
    }
}