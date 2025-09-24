package com.andrezktt.spring_ecommerce_api.dto;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        Long productId,
        String productName,
        int quantity,
        BigDecimal unitPrice
) {
}
