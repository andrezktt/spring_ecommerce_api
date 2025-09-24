package com.andrezktt.spring_ecommerce_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long orderId,
        Long customerId,
        LocalDateTime orderDate,
        String status,
        BigDecimal totalAmount,
        List<OrderItemResponseDTO> items
) {
}
