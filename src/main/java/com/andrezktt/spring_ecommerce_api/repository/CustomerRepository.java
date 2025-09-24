package com.andrezktt.spring_ecommerce_api.repository;

import com.andrezktt.spring_ecommerce_api.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
