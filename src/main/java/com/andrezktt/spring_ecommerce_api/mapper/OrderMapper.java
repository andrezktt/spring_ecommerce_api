package com.andrezktt.spring_ecommerce_api.mapper;

import com.andrezktt.spring_ecommerce_api.domain.OrderItem;
import com.andrezktt.spring_ecommerce_api.domain.PurchaseOrder;
import com.andrezktt.spring_ecommerce_api.dto.OrderItemResponseDTO;
import com.andrezktt.spring_ecommerce_api.dto.OrderResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "orderItems", target = "items")
    OrderResponseDTO toResponseDTO(PurchaseOrder order);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    OrderItemResponseDTO toOrderItemDTO(OrderItem orderItem);

    List<OrderResponseDTO> toResponseDTOList(List<PurchaseOrder> orders);
}
