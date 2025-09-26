package com.andrezktt.spring_ecommerce_api.mapper;

import com.andrezktt.spring_ecommerce_api.domain.Product;
import com.andrezktt.spring_ecommerce_api.dto.ProductRequestDTO;
import com.andrezktt.spring_ecommerce_api.dto.ProductResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponseDTO toResponseDTO(Product product);

    Product toEntity(ProductRequestDTO productRequestDTO);

    void updateProductFromDTO(ProductRequestDTO dto, @MappingTarget Product product);
}
