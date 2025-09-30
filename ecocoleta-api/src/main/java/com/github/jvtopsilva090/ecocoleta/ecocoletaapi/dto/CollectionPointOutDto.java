package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.CollectionPoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionPointOutDto {
    private Integer id;
    private String name;
    private String formattedAddress;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public CollectionPointOutDto(CollectionPoint collectionPoint) {
        this.id = collectionPoint.getId();
        this.name = collectionPoint.getName();
        this.formattedAddress = collectionPoint.getFormattedAddress();
        this.latitude = collectionPoint.getLatitude();
        this.longitude = collectionPoint.getLongitude();
    }
}
