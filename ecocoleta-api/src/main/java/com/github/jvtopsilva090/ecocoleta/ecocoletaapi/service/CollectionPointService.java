package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.service;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto.*;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.CollectionPoint;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.exception.CollectionPointException;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.exception.CollectionPointNotFoundException;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.repository.CollectionPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CollectionPointService {

    private final CollectionPointRepository collectionPointRepository;

    public CollectionPointOutDto createCollectionPoint(final CollectionPointCreateDto collectionPointCreateDto) {
        try {
            final CollectionPoint collectionPoint;
            collectionPoint = this.collectionPointRepository.save(new CollectionPoint(collectionPointCreateDto));
            return new CollectionPointOutDto(collectionPoint);
        } catch (Exception e) {
            throw new CollectionPointException("Failed to create collection point!");
        }
    }

    public List<CollectionPoint> getAllCollectionPoints() {
        try {
            return this.collectionPointRepository.findAll();
        } catch (Exception e) {
            throw new CollectionPointException("Failed to get collection points!");
        }
    }

    public CollectionPointOutDto getCollectionPointById(final Integer collectionPointId) {
        try {
            final CollectionPoint collectionPoint = this
                    .collectionPointRepository
                    .findById(collectionPointId)
                    .orElse(null);

            if (collectionPoint == null) return null;
            return new CollectionPointOutDto(collectionPoint);
        } catch (CollectionPointNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CollectionPointException("Failed to get collection point by id!");
        }
    }

    @Transactional
    public List<CollectionPointOutDto> updateCollectionPoint(final CollectionPointEditDto collectionPointEditDto) {
        try {
            final List<CollectionPoint> collectionPoints = new ArrayList<>();

            final CollectionPoint collectionPoint;

            collectionPoint = collectionPointRepository
                    .findById(collectionPointEditDto.id())
                    .orElseThrow(() -> new CollectionPointNotFoundException("Collection Point not found for id " + collectionPointEditDto.id()));

            Optional.ofNullable(collectionPointEditDto.name()).ifPresent(collectionPoint::setName);
            Optional.ofNullable(collectionPointEditDto.formattedAddress()).ifPresent(collectionPoint::setFormattedAddress);
            Optional.ofNullable(collectionPointEditDto.latitude()).ifPresent(collectionPoint::setLatitude);
            Optional.ofNullable(collectionPointEditDto.longitude()).ifPresent(collectionPoint::setLongitude);

            collectionPoints.add(collectionPoint);

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
            this.collectionPointRepository.deleteById(collectionPointId);
        } catch (Exception e) {
            throw new CollectionPointException("Failed to delete collection point!");
        }
    }
}