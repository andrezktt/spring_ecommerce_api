package com.andrezktt.spring_ecommerce_api.dto;

public record CustomerResponseDTO(
        Long id,
        String name,
        String email,
        String address
) {
}
