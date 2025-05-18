package com.fiap.techChallenge.application.services;

import com.fiap.techChallenge.application.useCases.CustomerUseCase;
import com.fiap.techChallenge.domain.customer.Customer;
import com.fiap.techChallenge.domain.customer.CustomerRepository;
import com.fiap.techChallenge.domain.customer.CustomerRequestDTO;
import com.fiap.techChallenge.utils.exceptions.EntityNotFoundException;
import com.fiap.techChallenge.utils.exceptions.UserAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerUseCase {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(CustomerRequestDTO customer) {
        Optional<Customer> optCustomer = customerRepository.findByCPF(customer.cpf());

        System.out.println(customer);

        if (optCustomer.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        Customer toDomain = new Customer();

        if (customer.anonymous()) {
            toDomain.setName(null);
            toDomain.setCpf(null);
            toDomain.setEmail(null);
            toDomain.setAnonymous(true);
        } else {
            toDomain.setName(customer.name());
            toDomain.setCpf(customer.cpf());
            toDomain.setEmail(customer.email());
            toDomain.setAnonymous(false);
        }

        return customerRepository.save(toDomain);
    }

    @Override
    public Customer searchCustomer(UUID id) {
        Optional<Customer> optCustomer = customerRepository.findById(id);

        if (optCustomer.isEmpty()) {
            throw new EntityNotFoundException("Customer");
        }

        return optCustomer.get();
    }

    @Override
    public Customer searchCustomer(String cpf) {
        Optional<Customer> optCustomer = customerRepository.findByCPF(cpf);

        if (optCustomer.isEmpty()) {
            throw new EntityNotFoundException("Customer");
        }

        return optCustomer.get();
    }
}
