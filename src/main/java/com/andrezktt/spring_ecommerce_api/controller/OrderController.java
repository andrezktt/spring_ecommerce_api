package com.andrezktt.spring_ecommerce_api.controller;

import com.andrezktt.spring_ecommerce_api.dto.OrderRequestDTO;
import com.andrezktt.spring_ecommerce_api.dto.OrderResponseDTO;
import com.andrezktt.spring_ecommerce_api.dto.UpdateOrderStatusDTO;
import com.andrezktt.spring_ecommerce_api.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO requestDTO) {
        OrderResponseDTO createdOrder = orderService.createOrder(requestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdOrder.orderId()).toUri();
        return ResponseEntity.created(location).body(createdOrder);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByCustomer(@RequestParam Long customerId) {
        List<OrderResponseDTO> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusDTO statusDTO) {
        OrderResponseDTO updatedOrder = orderService.updateOrderStatus(orderId, statusDTO);
        return ResponseEntity.ok(updatedOrder);
    }
}
