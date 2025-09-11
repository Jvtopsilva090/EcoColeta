package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto.CollectionPointCreateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "collection_point", uniqueConstraints = @UniqueConstraint(columnNames = {"latitude", "longitude"}))
public class CollectionPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "formatted_address", nullable = false)
    private String formattedAddress;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    public CollectionPoint(CollectionPointCreateDto collectionPointCreateDto) {
        this.name = collectionPointCreateDto.name();
        this.formattedAddress = collectionPointCreateDto.formattedAddress();
        this.latitude = collectionPointCreateDto.latitude();
        this.longitude = collectionPointCreateDto.longitude();
    }
}