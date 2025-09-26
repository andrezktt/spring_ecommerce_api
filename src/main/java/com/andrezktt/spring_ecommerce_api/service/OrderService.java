package com.andrezktt.spring_ecommerce_api.service;

import com.andrezktt.spring_ecommerce_api.domain.*;
import com.andrezktt.spring_ecommerce_api.dto.*;
import com.andrezktt.spring_ecommerce_api.mapper.OrderMapper;
import com.andrezktt.spring_ecommerce_api.repository.CustomerRepository;
import com.andrezktt.spring_ecommerce_api.repository.ProductRepository;
import com.andrezktt.spring_ecommerce_api.repository.PurchaseOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderService(PurchaseOrderRepository purchaseOrderRepository,
                        CustomerRepository customerRepository,
                        ProductRepository productRepository,
                        OrderMapper orderMapper) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO requestDTO) {
        Customer customer = customerRepository.findById(requestDTO.customerId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado."));

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setCustomer(customer);
        purchaseOrder.setOrderDate(LocalDateTime.now());
        purchaseOrder.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemDTO : requestDTO.items()) {
            Product product = productRepository.findById(itemDTO.productId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado: " + itemDTO.productId()));

            if (product.getStockQuantity() < itemDTO.quantity()) {
                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + product.getName());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(purchaseOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.quantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItems.add(orderItem);

            product.setStockQuantity(product.getStockQuantity() - itemDTO.quantity());

            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(itemDTO.quantity())));
        }

        purchaseOrder.setOrderItems(orderItems);
        purchaseOrder.setTotalAmount(totalAmount);
        PurchaseOrder savedOrder = purchaseOrderRepository.save(purchaseOrder);

        return orderMapper.toResponseDTO(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersByCustomerId(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException("Cliente não encontrado.");
        }
        List<PurchaseOrder> orders = purchaseOrderRepository.findByCustomerId(customerId);
        return orderMapper.toResponseDTOList(orders);
    }

    @Transactional
    public OrderResponseDTO updateOrderStatus(Long orderId, UpdateOrderStatusDTO statusDTO) {
        PurchaseOrder order = purchaseOrderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com o id: " + orderId));
        order.setStatus(statusDTO.newStatus());
        PurchaseOrder updatedOrder = purchaseOrderRepository.save(order);
        return orderMapper.toResponseDTO(updatedOrder);
    }
}
