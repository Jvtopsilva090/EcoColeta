package com.github.jvtopsilva090.ecocoletacli.repository;

import com.github.jvtopsilva090.ecocoletacli.dto.*;
import com.github.jvtopsilva090.ecocoletacli.util.RestClientHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EcoColetaApiRepository {

    private final String BASE_URL = "http://localhost:8080/api";
    private final RestClientHelper restClientHelper;

    public CollectionPointOutDto createCollectionPoint(final CollectionPointCreateDto collectionPointCreateDto) {
        final ParameterizedTypeReference<ApiResponseDto<CollectionPointOutDto>> typeReference = new ParameterizedTypeReference<>() {};
        return this.restClientHelper.post(BASE_URL.concat("/collection-point"), typeReference, collectionPointCreateDto).data();
    }

    public List<CollectionPointOutDto> getAllCollectionPoints() {
        return this.restClientHelper
            .get(
                BASE_URL.concat("/collection-point?").concat("&page=0&size=1000"),
                new ParameterizedTypeReference<ApiResponseDto<List<CollectionPointOutDto>>>() {}
            ).data();
    }

    public CollectionPointOutDto getCollectionPointById(Integer idCollectionPoint) {
        return this.restClientHelper
            .get(
                BASE_URL.concat("/collection-point/").concat(idCollectionPoint.toString()),
                new ParameterizedTypeReference<ApiResponseDto<CollectionPointOutDto>>() {}
            ).data();
    }

    public CollectionPointOutDto updateCollectionPoint(final CollectionPointEditDto collectionPointEditDto) {
        return this.restClientHelper
                .put(
                    BASE_URL.concat("/collection-point"),
                    new ParameterizedTypeReference<ApiResponseDto<CollectionPointOutDto>>() {},
                    collectionPointEditDto
                ).data();
    }

    public void deleteCollectionPoint(Integer collectionPointId) {
        this.restClientHelper.delete(BASE_URL.concat("/collection-point/").concat(collectionPointId.toString()));
    }
}
