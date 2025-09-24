package com.andrezktt.spring_ecommerce_api.service;

import com.andrezktt.spring_ecommerce_api.domain.Customer;
import com.andrezktt.spring_ecommerce_api.dto.CustomerRequestDTO;
import com.andrezktt.spring_ecommerce_api.dto.CustomerResponseDTO;
import com.andrezktt.spring_ecommerce_api.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponseDTO createCustomer(CustomerRequestDTO requestDTO) {
        Customer customer = new Customer();
        customer.setName(requestDTO.name());
        customer.setEmail(requestDTO.email());
        customer.setPassword(requestDTO.password());
        customer.setAddress(requestDTO.address());

        Customer savedCustomer = customerRepository.save(customer);
        return toResponseDTO(savedCustomer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerResponseDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o id: " + id));
    }

    @Transactional
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO requestDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o id: " + id));

        customer.setName(requestDTO.name());
        customer.setEmail(requestDTO.email());
        if (requestDTO.password() != null && !requestDTO.password().isEmpty()) {
            customer.setPassword(requestDTO.password());
        }
        customer.setAddress(requestDTO.address());

        Customer updatedCustomer = customerRepository.save(customer);
        return toResponseDTO(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente não encontrado com o id: " + id);
        }
        customerRepository.deleteById(id);
    }

    private CustomerResponseDTO toResponseDTO(Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
