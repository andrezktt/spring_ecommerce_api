package com.andrezktt.spring_ecommerce_api.service;

import com.andrezktt.spring_ecommerce_api.domain.Product;
import com.andrezktt.spring_ecommerce_api.dto.ProductRequestDTO;
import com.andrezktt.spring_ecommerce_api.dto.ProductResponseDTO;
import com.andrezktt.spring_ecommerce_api.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "Notebook Gamer", "Notebook de alta performance", new BigDecimal("7500.00"), 50);
        requestDTO = new ProductRequestDTO("Notebook Gamer", "Notebook de alta performance", new BigDecimal("7500.00"), 50);
    }

    @Test
    @DisplayName(("Deve criar um produto com sucesso"))
    void createProduct_Success() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        ProductResponseDTO response = productService.createProduct(requestDTO);

        assertNotNull(response);
        assertEquals("Notebook Gamer", response.name());
        assertEquals(1L, response.id());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Deve retornar um produto pelo ID com sucesso")
    void getProductById_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDTO response = productService.getProductById(1L);

        assertNotNull(response);
        assertEquals(product.getId(), response.id());
    }

    @Test
    @DisplayName("Deve retornar uma lista de todos os produtos")
    void getAllProducts_Success() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));

        List<ProductResponseDTO> response = productService.getAllProducts();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        assertEquals("Notebook Gamer", response.getFirst().name());
    }

    @Test
    @DisplayName("Deve deletar um produto com sucesso")
    void deleteProduct_Success() {
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);

        assertDoesNotThrow(() -> productService.deleteProduct(1L));

        verify(productRepository, times(1)).deleteById(1L);
    }
}
