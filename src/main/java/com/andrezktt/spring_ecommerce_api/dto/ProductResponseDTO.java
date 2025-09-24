package com.andrezktt.spring_ecommerce_api.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        int stockQuantity
) {
}
