package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.projection;

import java.math.BigDecimal;

public interface CollectionPointFlatProjection {
    Integer getCollectionPointId();
    String getCollectionPointName();
    String getCollectionPointAddress();
    BigDecimal getCollectionPointLatitude();
    BigDecimal getCollectionPointLongitude();
    Integer getResidueId();
    String getResidueName();
}
