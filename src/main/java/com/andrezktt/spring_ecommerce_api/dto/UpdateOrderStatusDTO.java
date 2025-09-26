package com.andrezktt.spring_ecommerce_api.dto;

import com.andrezktt.spring_ecommerce_api.domain.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusDTO(
        @NotNull(message = "O novo status não pode ser nulo.")
        OrderStatus newStatus
) {
}
