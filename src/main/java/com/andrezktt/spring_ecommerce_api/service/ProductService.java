package com.andrezktt.spring_ecommerce_api.service;

import com.andrezktt.spring_ecommerce_api.domain.Product;
import com.andrezktt.spring_ecommerce_api.dto.ProductRequestDTO;
import com.andrezktt.spring_ecommerce_api.dto.ProductResponseDTO;
import com.andrezktt.spring_ecommerce_api.mapper.ProductMapper;
import com.andrezktt.spring_ecommerce_api.repository.OrderItemRepository;
import com.andrezktt.spring_ecommerce_api.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, OrderItemRepository orderItemRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        Product product = productMapper.toEntity(requestDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponseDTO(savedProduct);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(productMapper::toResponseDTO);
    }

    @Cacheable(value = "products", key = "#id")
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o id: " + id));
        return productMapper.toResponseDTO(product);
    }

    @Transactional
    public Page<ProductResponseDTO> searchProducts(String name, Pageable pageable) {
        Page<Product> productPage = productRepository.findByNameContainingIgnoreCase(name, pageable);
        return productPage.map(productMapper::toResponseDTO);
    }

    @CachePut(value = "products", key = "#id")
    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o id: " + id));

        productMapper.updateProductFromDTO(requestDTO, product);

        Product updatedProduct = productRepository.save(product);
        return productMapper.toResponseDTO(updatedProduct);
    }

    @CacheEvict(value = "products", key = "#id")
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Produto não encontrado com o id: " + id);
        }
        if (orderItemRepository.existsByProductId(id)) {
            throw new IllegalStateException("Não é possível excluir um produto que já está associado a um pedido.");
        }
        productRepository.deleteById(id);
    }
}
