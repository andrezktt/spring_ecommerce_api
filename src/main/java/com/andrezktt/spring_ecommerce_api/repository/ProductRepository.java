package com.andrezktt.spring_ecommerce_api.repository;

import com.andrezktt.spring_ecommerce_api.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
