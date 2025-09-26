package com.andrezktt.spring_ecommerce_api.dto;

import java.time.Instant;

public record ErrorResponseDTO(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
