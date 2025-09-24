package com.andrezktt.spring_ecommerce_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestDTO(
        @NotNull Long customerId,
        @NotEmpty List<@Valid OrderItemRequestDTO> items
        ) {
}
