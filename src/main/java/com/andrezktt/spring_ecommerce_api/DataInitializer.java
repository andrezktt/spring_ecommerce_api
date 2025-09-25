package com.andrezktt.spring_ecommerce_api;

import com.andrezktt.spring_ecommerce_api.domain.Customer;
import com.andrezktt.spring_ecommerce_api.domain.Role;
import com.andrezktt.spring_ecommerce_api.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (customerRepository.count() == 0) {
            System.out.println("Nenhum usuário encontrado, criando usuário ADMIN padrão...");

            Customer adminUser = new Customer();
            adminUser.setName("Admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setAddress("Rua Admin, 123");
            adminUser.setRole(Role.ROLE_ADMIN);

            customerRepository.save(adminUser);
            System.out.println("Usuário administrador padrão criado com sucesso!");
        } else {
            System.out.println("O banco de dados já contém usuários. Nenhuma ação necessária.");
        }
    }
}
