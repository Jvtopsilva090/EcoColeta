package com.github.jvtopsilva090.ecocoletacli.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestClientHelper {

    public <T, R> T post(String url, ParameterizedTypeReference<T> typeRef, R body) {
        final RestClient restClient;
        final ResponseEntity<T> responseEntity;
        final HttpStatusCode statusCode;

        restClient = RestClient.create();
        responseEntity = restClient
                .post()
                .uri(url)
                .body(body)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(typeRef);

        statusCode = responseEntity.getStatusCode();

        if (!statusCode.is2xxSuccessful()) throw new RuntimeException("Erro ao consultar API.");

        return responseEntity.getBody();
    }

    public <T> T get(String url, ParameterizedTypeReference<T> typeRef) {
        final RestClient restClient;
        final ResponseEntity<T> responseEntity;
        final HttpStatusCode statusCode;

        restClient = RestClient.create();
        responseEntity = restClient
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(typeRef);

        statusCode = responseEntity.getStatusCode();

        if (!statusCode.is2xxSuccessful()) throw new RuntimeException("Erro ao consultar API.");

        return responseEntity.getBody();
    }

    public <T, R> T put(String url, ParameterizedTypeReference<T> typeRef, R body) {
        final RestClient restClient;
        final ResponseEntity<T> responseEntity;
        final HttpStatusCode statusCode;

        restClient = RestClient.create();
        responseEntity = restClient
                .put()
                .uri(url)
                .body(body)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(typeRef);

        statusCode = responseEntity.getStatusCode();

        if (!statusCode.is2xxSuccessful()) throw new RuntimeException("Erro ao consultar API.");

        return responseEntity.getBody();
    }

    public void delete(String url) {
        final RestClient restClient;
        final ResponseEntity<Object> responseEntity;
        final HttpStatusCode statusCode;

        restClient = RestClient.create();
        responseEntity = restClient
                .delete()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Object.class);

        statusCode = responseEntity.getStatusCode();

        if (!statusCode.is2xxSuccessful()) throw new RuntimeException("Erro ao consultar API.");
    }
}
