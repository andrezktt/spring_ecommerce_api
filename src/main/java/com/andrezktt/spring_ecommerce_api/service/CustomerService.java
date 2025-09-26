package com.andrezktt.spring_ecommerce_api.service;

import com.andrezktt.spring_ecommerce_api.domain.Customer;
import com.andrezktt.spring_ecommerce_api.domain.Role;
import com.andrezktt.spring_ecommerce_api.dto.CustomerRequestDTO;
import com.andrezktt.spring_ecommerce_api.dto.CustomerResponseDTO;
import com.andrezktt.spring_ecommerce_api.mapper.CustomerMapper;
import com.andrezktt.spring_ecommerce_api.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerMapper = customerMapper;
    }

    @Transactional
    public CustomerResponseDTO createCustomer(CustomerRequestDTO requestDTO) {
        Customer customer = customerMapper.toEntity(requestDTO);
        customer.setPassword(passwordEncoder.encode(requestDTO.password()));
        customer.setRole(Role.ROLE_CUSTOMER);

        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toResponseDTO(savedCustomer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerResponseDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o id: " + id));
    }

    @Transactional
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO requestDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o id: " + id));

        customerMapper.updateCustomerFromDTO(requestDTO, customer);

        if (requestDTO.password() != null && !requestDTO.password().isEmpty()) {
            customer.setPassword(passwordEncoder.encode(requestDTO.password()));
        }

        Customer updatedCustomer = customerRepository.save(customer);
        return customerMapper.toResponseDTO(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente não encontrado com o id: " + id);
        }
        customerRepository.deleteById(id);
    }
}
