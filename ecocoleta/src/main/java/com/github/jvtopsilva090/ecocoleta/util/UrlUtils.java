package com.github.jvtopsilva090.ecocoleta.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class UrlUtils {

    public static String buildUrl(String endpoint, Object params) {
        if (Objects.isNull(params)) return endpoint;

        final StringBuilder stringBuilder;
        stringBuilder = new StringBuilder(endpoint);

        stringBuilder.append("?");
        stringBuilder.append(toQueryString(params));

        return stringBuilder.toString();
    }

    private static String toQueryString(Object params) {
        final ObjectMapper objectMapper;
        final Map<String, Object> mappedFields;

        objectMapper = new ObjectMapper();
        mappedFields = objectMapper.convertValue(params, Map.class);

        return mappedFields.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }
}