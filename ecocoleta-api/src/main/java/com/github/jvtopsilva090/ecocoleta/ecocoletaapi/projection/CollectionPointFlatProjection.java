package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.projection;

import java.math.BigDecimal;

public interface CollectionPointFlatProjection {
    Integer getIdCollectionPoint();
    String getNameCollectionPoint();
    String getAddressCollectionPoint();
    BigDecimal getLatitude();
    BigDecimal getLongitude();
    Integer getIdResidue();
    String getNameResidue();
}
