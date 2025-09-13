package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.config;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.dto.ApiResponseDto;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.exception.CollectionPointException;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.exception.CollectionPointNotFoundException;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.exception.ResidueException;
import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.exception.ResidueNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Object>> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponseDto<>(false, e.getMessage(), e.toString()));
    }

    @ExceptionHandler(CollectionPointException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleCollectionPointException(CollectionPointException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDto<>(false, e.getMessage(), e.toString()));
    }

    @ExceptionHandler(CollectionPointNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleCollectionPointNotFoundException(CollectionPointNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDto<>(false, e.getMessage(), e.toString()));
    }

    @ExceptionHandler(ResidueException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleResidueException(ResidueException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDto<>(false, e.getMessage(), e.toString()));
    }

    @ExceptionHandler(ResidueNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleResidueNotFoundException(ResidueNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDto<>(false, e.getMessage(), e.toString()));
    }
}
