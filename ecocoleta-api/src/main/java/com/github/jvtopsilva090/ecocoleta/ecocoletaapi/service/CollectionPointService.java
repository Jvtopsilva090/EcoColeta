package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.service;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto.*;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.CollectionPoint;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.Residue;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.ResiduesCollectionPoint;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.exception.CollectionPointException;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.exception.CollectionPointNotFoundException;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.repository.CollectionPointRepository;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.repository.ResiduesCollectionPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CollectionPointService {

    private final CollectionPointRepository collectionPointRepository;
    private final ResiduesCollectionPointRepository residuesCollectionPointRepository;

    public CollectionPointOutDto createCollectionPoint(final CollectionPointCreateDto collectionPointCreateDto) {
        try {
            final CollectionPoint collectionPoint;
            collectionPoint = this.collectionPointRepository.save(new CollectionPoint(collectionPointCreateDto));
            CollectionPointOutDto outDto = new CollectionPointOutDto(collectionPoint);
            Optional.ofNullable(collectionPointCreateDto.residueIds()).ifPresent(residueIds -> {
                residuesCollectionPointRepository
                    .saveAll(
                        residueIds.stream().map(residueId -> new ResiduesCollectionPoint(collectionPoint.getId(), residueId)).toList()
                    );

                List<Residue> residues = residuesCollectionPointRepository.getResidueByIdCollectionPoint(collectionPoint.getId());
                outDto.setResiduesType(residues);
            });
            return outDto;
        } catch (Exception e) {
            throw new CollectionPointException("Failed to create collection point!");
        }
    }

    public List<CollectionPointOutDto> getAllCollectionPoints(String residueType, String name) {
        try {
            final Map<Integer, CollectionPointOutDto> map = new HashMap<>();
            this.collectionPointRepository
                    .findAll(residueType, name)
                    .forEach(collectionPoint -> {
                        map.computeIfAbsent(
                            collectionPoint.getIdCollectionPoint(),
                            k -> new CollectionPointOutDto(collectionPoint)
                        ).addResidue(new Residue(collectionPoint.getIdResidue(), collectionPoint.getNameResidue()));
                    });
            return map.values().stream().toList();
        } catch (Exception e) {
            throw new CollectionPointException("Failed to get collection points!");
        }
    }

    public CollectionPointOutDto getCollectionPointById(final Integer collectionPointId) {
        try {
            final CollectionPointOutDto outDto;

            final CollectionPoint collectionPoint = this
                    .collectionPointRepository
                    .findById(collectionPointId)
                    .orElse(null);

            if (collectionPoint == null) return null;

            outDto = new CollectionPointOutDto(collectionPoint);
            List<Residue> residues = residuesCollectionPointRepository.getResidueByIdCollectionPoint(collectionPoint.getId());
            outDto.setResiduesType(residues);

            return outDto;
        } catch (CollectionPointNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CollectionPointException("Failed to get collection point by id!");
        }
    }

    @Transactional
    public CollectionPointOutDto updateCollectionPoint(final CollectionPointEditDto collectionPointEditDto) {
        try {
            final CollectionPoint collectionPoint;
            final CollectionPointOutDto outDto;

            collectionPoint = collectionPointRepository
                    .findById(collectionPointEditDto.id())
                    .orElseThrow(() -> new CollectionPointNotFoundException("Collection Point not found for id " + collectionPointEditDto.id()));

            Optional.ofNullable(collectionPointEditDto.name()).ifPresent(collectionPoint::setName);
            Optional.ofNullable(collectionPointEditDto.formattedAddress()).ifPresent(collectionPoint::setFormattedAddress);
            Optional.ofNullable(collectionPointEditDto.latitude()).ifPresent(collectionPoint::setLatitude);
            Optional.ofNullable(collectionPointEditDto.longitude()).ifPresent(collectionPoint::setLongitude);

            outDto = new CollectionPointOutDto(
                this.collectionPointRepository.save(collectionPoint)
            );

            Optional.ofNullable(collectionPointEditDto.residueIds()).ifPresent(residueIds -> {
                this.residuesCollectionPointRepository.deleteByIdCollectionPoint(collectionPoint.getId());

                residuesCollectionPointRepository
                        .saveAll(
                                residueIds.stream().map(residueId -> new ResiduesCollectionPoint(collectionPoint.getId(), residueId)).toList()
                        );

                List<Residue> residues = residuesCollectionPointRepository.getResidueByIdCollectionPoint(collectionPoint.getId());
                outDto.setResiduesType(residues);
            });

            return outDto;
        } catch (CollectionPointNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CollectionPointException("Failed to delete collection point!");
        }
    }

    @Transactional
    public void deleteCollectionPoint(final Integer collectionPointId) {
        try {
            this.residuesCollectionPointRepository.deleteByIdCollectionPoint(collectionPointId);
            this.collectionPointRepository.deleteById(collectionPointId);
        } catch (Exception e) {
            throw new CollectionPointException("Failed to delete collection point!");
        }
    }
}