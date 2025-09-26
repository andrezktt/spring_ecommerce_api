package com.andrezktt.spring_ecommerce_api.mapper;

import com.andrezktt.spring_ecommerce_api.domain.Customer;
import com.andrezktt.spring_ecommerce_api.dto.CustomerRequestDTO;
import com.andrezktt.spring_ecommerce_api.dto.CustomerResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponseDTO toResponseDTO(Customer customer);

    Customer toEntity(CustomerRequestDTO requestDTO);

    void updateCustomerFromDTO(CustomerRequestDTO dto, @MappingTarget Customer customer);
}
