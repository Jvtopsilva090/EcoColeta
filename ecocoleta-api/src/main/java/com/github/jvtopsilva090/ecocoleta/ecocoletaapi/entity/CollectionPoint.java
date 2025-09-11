package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto.CollectionPointCreateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "collection_point", uniqueConstraints = @UniqueConstraint(columnNames = {"latitude", "longitude"}))
public class CollectionPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "formatted_address", nullable = false)
    private String formattedAddress;

    @Column(name = "latitude", precision = 10, scale = 8, nullable = false)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 10, scale = 8, nullable = false)
    private BigDecimal longitude;

    public CollectionPoint(CollectionPointCreateDto collectionPointCreateDto) {
        this.name = collectionPointCreateDto.name();
        this.formattedAddress = collectionPointCreateDto.formattedAddress();
        this.latitude = collectionPointCreateDto.latitude();
        this.longitude = collectionPointCreateDto.longitude();
    }
}