package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.CollectionPoint;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.entity.Residue;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.projection.CollectionPointFlatProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionPointOutDto {
    private Integer id;
    private String name;
    private String formattedAddress;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private List<Residue> residuesType = new ArrayList<>();

    public CollectionPointOutDto(CollectionPoint collectionPoint) {
        this.id = collectionPoint.getId();
        this.name = collectionPoint.getName();
        this.formattedAddress = collectionPoint.getFormattedAddress();
        this.latitude = collectionPoint.getLatitude();
        this.longitude = collectionPoint.getLongitude();
    }

    public CollectionPointOutDto(CollectionPointFlatProjection collectionPoint) {
        this.id = collectionPoint.getIdCollectionPoint();
        this.name = collectionPoint.getNameCollectionPoint();
        this.formattedAddress = collectionPoint.getAddressCollectionPoint();
        this.latitude = collectionPoint.getLatitude();
        this.longitude = collectionPoint.getLongitude();
    }

    public void addResidue(Residue residue) {
        residuesType.add(residue);
    }
}
